package com.leader.ren.service.system;

import com.google.code.kaptcha.Producer;
import com.leader.ren.component.config.CacheAsyncService;
import com.leader.ren.component.config.CodeComponent;
import com.leader.ren.common.constant.Common;
import com.leader.ren.common.constant.RestMsg;
import com.leader.ren.common.jwt.IJWTInfo;
import com.leader.ren.common.jwt.JWTHelper;
import com.leader.ren.common.jwt.JWTInfo;
import com.leader.ren.common.utils.CommUtils;
import com.leader.ren.common.utils.MD5;
import com.leader.ren.common.utils.MyBeanUtils;
import com.leader.ren.common.dto.RestVo;
import com.leader.ren.mapper.system.SysCoreElementMapper;
import com.leader.ren.mapper.system.SysCoreMenuMapper;
import com.leader.ren.mapper.system.SysCoreRoleMapper;
import com.leader.ren.mapper.system.SysCoreUserMapper;
import com.leader.ren.model.system.bo.AuthBo;
import com.leader.ren.model.system.entity.ElementEntity;
import com.leader.ren.model.system.entity.MenuEntity;
import com.leader.ren.model.system.entity.SysCoreRole;
import com.leader.ren.model.system.entity.SysCoreUser;
import com.leader.ren.model.system.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * 描述
 *
 * @author: renpenghui
 * @date: 2019-08-27 17:37
 **/
@Service
@Slf4j
public class AuthService {
    // 密参
    @Value("${sys.security.salt}")
    private String salt;
    // 公钥
    @Value("${sys.security.publicKey}")
    private String publicKey;
    // 私钥
    @Value("${sys.security.privateKey}")
    private String privateKey;
    // 令牌过期时间
    @Value("${jwt.user.token-expire}")
    private Integer tokenExpire;

    @Autowired
    CacheAsyncService cacheAsyncService;

    @Resource
    Producer codeProducer;

    @Autowired(required = false)
    private SysCoreUserMapper userMapper;

    @Autowired
    private SysCoreRoleMapper roleMapper;

    @Autowired
    private SysCoreMenuMapper menuMapper;

    @Autowired
    private SysCoreElementMapper elementMapper;

    /**
     * 图片验证码生成
     *
     * @return
     * @throws Exception
     */
    public RestVo<AuthVo> codeMaker() {
        try {
            AuthVo authVo = new AuthVo();
            String[] result = CodeComponent.makeBase64Code(codeProducer);
            String code = result[1];
            if(!CommUtils.isNull(code)){
                String token = MD5.sign(code + Calendar.getInstance().getTime(), salt);
                cacheAsyncService.add(token, code, 2);
                authVo.setToken(token);
            }
            log.info("打印验证码：{},嘿嘿嘿嘿嘿",code);
            authVo.setCodeImg(result[0]);
            return RestVo.SUCCESS(authVo);
        } catch (Exception e) {
            log.error("异常信息", e);
            return RestVo.FAIL(RestMsg.ERROR);
        }
    }


    /**
     * 用户登录（密码登录、图片验证码）
     * @param bo
     * @return
     * @throws Exception
     */
    public RestVo<AuthVo> codeImgLogin(AuthBo bo) {
        try{
            String token = bo.getToken();
            String codeImg = bo.getCodeImg();
            String codeCache = (String) cacheAsyncService.get(token);
            if(!codeImg.equals(codeCache)){
                return RestVo.FAIL(RestMsg.AUTH_CODE_ERROR);
            } else {
                return this.login(bo);
            }
        } catch (Exception e){
            log.error("异常信息", e);
            return RestVo.FAIL(RestMsg.ERROR);
        }
    }

    /**
     * 用户登录
     * @param bo
     * @return
     * @throws Exception
     */
    public RestVo<AuthVo> login(AuthBo bo) throws Exception {
        String username = bo.getUsername();
        String password = MD5.sign(bo.getPassword(), salt);
        String authKey = MD5.sign(username + "-" + password, salt);
        // 多点登录直接取得并刷新令牌有效期（单点登录时需要屏蔽掉以下代码）
        String token = (String) cacheAsyncService.get(Common.USER_AUTH_TOKEN + authKey);
        if(!StringUtils.isEmpty(token)){
            cacheAsyncService.add(Common.USER_AUTH_TOKEN + authKey, token, tokenExpire / 60);

            AuthVo authVo = new AuthVo();
            authVo.setToken(token);
        }
        // 多点登录直接取得并刷新令牌有效期（单点登录时需要屏蔽掉以上代码）

        // 单点登录时每次重新生成令牌
        SysCoreUser entity = userMapper.selectByAuthInfo(username, password);
        if(CommUtils.isNull(entity)){
            // 登录失败
            log.info("用户信息取得失败->username:" + username + ",password->" + password + ".");
            return RestVo.FAIL(RestMsg.USERNAME_OR_PASSWORD_ERROR);
        }


        UserVo vo = new UserVo();
        BeanUtils.copyProperties(entity, vo);

        Date expireDate = DateTime.now().plusSeconds(tokenExpire).toDate();
        JWTInfo jwtInfo = new JWTInfo(entity.getId().toString(), entity.getUsername(),
                entity.getPhone(), DateTime.now().getMillis());
        token = JWTHelper.encodeToken(jwtInfo, privateKey, expireDate);

        cacheAsyncService.expire(Common.USER_AUTH_TOKEN + authKey, tokenExpire);
        cacheAsyncService.add(Common.USER_LOGIN_HY_ID + token, String.valueOf(entity.getId()));

        if(StringUtils.isEmpty(token)){
            log.info("用户令牌生成失败->username:" + username + ",password->" + password + ".");
            return RestVo.FAIL(RestMsg.LOGIN_FAIL);
        } else {
            AuthVo authVo = new AuthVo();
            authVo.setToken(token);
            return RestVo.SUCCESS(authVo);
        }
    }



    /**
     * 取得用户相关信息
     *
     * @param token
     * @return
     * @throws Exception
     */
    public RestVo<AuthVo> getInfos(String token) throws Exception {
        // 用户信息
        UserVo userVo = getUserByToken(token);
        if(CommUtils.isNull(userVo)){
            return RestVo.FAIL(RestMsg.NOT_LOGIN);
        }
        userVo.setPassword(null);
        AuthVo authVo = new AuthVo();
        authVo.setToken(token);
        authVo.setUser(userVo);
        // 用户角色取得
        List<Long> roleIds = new ArrayList<>();
        List<RoleVo> roles = new ArrayList<>();
        List<SysCoreRole> roleEntities = roleMapper.selectLinkedRoleByUserId(userVo.getId());
        if(roleEntities.isEmpty()){
            authVo.setRoles(roles);
            List<MenuVo> menuList = new ArrayList<>();
            authVo.setMenus(menuList);
            List<ElementVo> elements = new ArrayList<>();
            authVo.setElements(elements);
            return RestVo.SUCCESS(authVo);
        }
        for(SysCoreRole roleEntity : roleEntities){
            RoleVo roleVo = new RoleVo();
            BeanUtils.copyProperties(roleEntity, roleVo);
            roles.add(roleVo);
            roleIds.add(roleVo.getId());
        }
        authVo.setRoles(roles);
        // 用户菜单取得
        List<MenuVo> menuList = new ArrayList<>();
        List<MenuEntity> menuEntitiesOfRole = menuMapper.selectByRoleIds(roleIds);
        for(MenuEntity menuEntity : menuEntitiesOfRole){
            MenuVo menuVo = new MenuVo();
            BeanUtils.copyProperties(menuEntity, menuVo);
            menuList.add(menuVo);
        }
        // 用户菜单列表
        authVo.setMenus(menuList);


        List<ElementVo> elements = new ArrayList<>();
        // 通过用户角色取得用户元素
        List<ElementEntity> elementEntitiesOfRole = elementMapper.selectByRoleIds(roleIds);
        for(ElementEntity elementEntity : elementEntitiesOfRole){
            ElementVo elementVo = new ElementVo();
            BeanUtils.copyProperties(elementEntity, elementVo);
            elements.add(elementVo);
        }

        authVo.setElements(elements);

        return RestVo.SUCCESS(authVo);
    }

    /**
     * 根据令牌取得用户信息
     * @param token
     * @return
     * @throws Exception
     */
    public UserVo getUserByToken(String token) throws Exception {
        UserVo vo = null;
        try{
            IJWTInfo jwtInfo = JWTHelper.parserToken(token, publicKey);
            SysCoreUser entity = userMapper.selectByPrimaryKey(Long.parseLong(jwtInfo.getId()));
            vo = new UserVo();
            BeanUtils.copyProperties(entity, vo);
        } catch (Exception e){
            log.error("异常信息", e);
        }
        return vo;
    }


    /**
     * 取得菜单列表
     *
     * @return
     */
    public RestVo<List<MenuVo>> getMenuList(){
        List<MenuVo> datas = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        List<MenuEntity> entities = menuMapper.selectSearchData(params, null, null);
        for(MenuEntity entity : entities){
            MenuVo vo = new MenuVo();
            BeanUtils.copyProperties(entity, vo);
            datas.add(vo);
        }
        return RestVo.SUCCESS(datas);
    }

    /**
     * 修改用户密码
     * @param curUid    当前登录用户
     * @param passWord  原密码
     * @param newPassWord  新密码
     * @return
     */
    public RestVo<Boolean> updatePassWord(Long curUid, String passWord, String newPassWord, String token){
        SysCoreUser curUser = userMapper.selectByPrimaryKey(curUid);
        passWord = MD5.sign(passWord, salt);
        //原密码错误返回
        if(!passWord.equals(curUser.getPassword())){
            return RestVo.FAIL(RestMsg.FAIL.getCode(),"原密码错误");
        }

        curUser.setPassword(MD5.sign(newPassWord, salt));
        int rs = userMapper.updateByPrimaryKeySelective(curUser);
        if(rs > 0){
            //modify fushuan 2019.01.19 修改密码后,清空token。
            if(!CommUtils.isNull(token)){
                cacheAsyncService.remove(Common.USER_LOGIN_HY_ID + token);
            }
            //modify fushuan 2019.01.19
            return RestVo.SUCCESS("修改成功");
        }
        return RestVo.FAIL("修改失败");
    }


    /**
     * 获取某个特定元素的权限
     * @return
     */
    public RestVo<List<ElementVo>> getElementPermission(Long curUid, List<String> elementCode){
        List<Long> roleIdList = roleMapper.getRoleIdByUserId(curUid);
        List<ElementEntity> list = elementMapper.getElementByCode(elementCode);
        List<ElementVo> voList = new ArrayList<>();
        for(ElementEntity entity : list){
            ElementVo vo = new ElementVo();
            MyBeanUtils.copyProperties(entity,vo);
            if(CommUtils.isNull(roleIdList)){
                vo.setFlag(Boolean.FALSE);
            }else{
                int i = elementMapper.existRoleIdAndElementId(roleIdList, entity.getId());
                if(i>0){
                    vo.setFlag(Boolean.TRUE);
                }else {
                    vo.setFlag(Boolean.FALSE);
                }
            }
            voList.add(vo);
        }
        return RestVo.SUCCESS(voList);
    }
}
