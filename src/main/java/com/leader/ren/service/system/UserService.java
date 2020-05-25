package com.leader.ren.service.system;


import com.github.pagehelper.PageHelper;
import com.leader.ren.common.dto.PageBo;
import com.leader.ren.common.constant.RestMsg;
import com.leader.ren.common.utils.CommUtils;
import com.leader.ren.common.utils.MD5;
import com.leader.ren.common.utils.PageInfoUtil;
import com.leader.ren.common.dto.PageVo;
import com.leader.ren.common.dto.RestVo;
import com.leader.ren.mapper.system.SysCoreRoleMapper;
import com.leader.ren.mapper.system.SysCoreUserMapper;
import com.leader.ren.mapper.system.SysCoreUserRoleLinkMapper;
import com.leader.ren.model.system.bo.RoleBo;
import com.leader.ren.model.system.bo.UserBo;
import com.leader.ren.model.system.entity.SysCoreRole;
import com.leader.ren.model.system.entity.SysCoreUser;
import com.leader.ren.model.system.entity.SysCoreUserRoleLink;
import com.leader.ren.model.system.enums.StatusEnum;
import com.leader.ren.model.system.vo.RoleVo;
import com.leader.ren.model.system.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 描述
 *
 * @author: renpenghui
 * @date: 2019-08-27 16:52
 **/
@Service
@Slf4j
public class UserService {

    // 公钥
    @Value("${sys.security.salt}")
    private String salt;

    @Autowired
    private SysCoreUserMapper userMapper;

    @Autowired
    private SysCoreRoleMapper roleMapper;

    @Autowired
    private SysCoreUserRoleLinkMapper userRoleLinkMapper;

    public SysCoreUser get(){
        return userMapper.selectByPrimaryKey(1L);
    }


    /**
     * 取得用户分页
     * @param pageBo 分页参数
     * @return 分页结果
     */
    public RestVo<PageVo<UserVo>> getPage(PageBo<UserBo> pageBo){
        if(!CommUtils.isNull(pageBo.getPage()) && !CommUtils.isNull(pageBo.getSize())){
            PageHelper.startPage(pageBo.getPage(),pageBo.getSize());
        }
        List<UserVo> datas = new ArrayList<>();
        List<SysCoreUser> entities = userMapper.selectSearchData(pageBo.getParam());
        for(SysCoreUser entity : entities){
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(entity, userVo);
            datas.add(userVo);
        }
        return RestVo.SUCCESS(PageInfoUtil.trans2PageVo(entities,datas));
    }

    public Long findUserNameCount(String username){
        Map<String, Object> params=new HashMap<>();
        params.put("username",username);
        params.put("status",1);
        return userMapper.selectNameCount(params);
    }

    /**
     * 创建用户信息
     * @param curUid 当前登录用户
     * @param bo 用户信息
     * @return
     */
    public UserVo create(Long curUid, UserBo bo) {
        SysCoreUser entity = new SysCoreUser();
        BeanUtils.copyProperties(bo, entity);

        // 非系统用户不能创建系统用户
        entity.setPassword(MD5.sign(entity.getPassword(), salt));
        entity.setCreateAt(new Date());
        entity.setCreateBy(curUid);
        int rs = userMapper.insert(entity);
        if(rs == 0){
            log.info("创建用户失败");
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(entity, userVo);
        return userVo;
    }


    /**
     * 取得用户信息
     * @param selUid 目标用户的ID
     * @return
     */
    public RestVo<UserVo> select(Long selUid) {
        SysCoreUser entity = userMapper.selectByPrimaryKey(selUid);
        UserVo vo = new UserVo();
        BeanUtils.copyProperties(entity, vo);
        return RestVo.SUCCESS(vo);
    }


    /**
     * 更新用户信息
     * @param curUid 当前登录用户
     * @param bo 用户信息
     * @return
     */
    public RestVo<Boolean> update(Long curUid, UserBo bo) {
        UserBo userBo = new UserBo();
        userBo.setUsername(bo.getUsername());
        userBo.setStatus(1);
        List<SysCoreUser> infoUserName = this.getUserInfoByCondition(userBo);
        if (null!=infoUserName && !infoUserName.isEmpty() && infoUserName.get(0).getId().longValue() !=bo.getId().longValue()){
            return RestVo.FAIL("用户名已存在!");
        }

        SysCoreUser entity = new SysCoreUser();
        BeanUtils.copyProperties(bo, entity);

        SysCoreUser userEntity = userMapper.selectByPrimaryKey(bo.getId());
        if(null == userEntity){
            return RestVo.FAIL(RestMsg.INVALID_DATA);
        }
        if (null != entity.getPassword()){
            entity.setPassword(MD5.sign(entity.getPassword(), salt));
        }
        entity.setUpdateAt(new Date());
        entity.setUpdateBy(curUid);
        int rs = userMapper.updateByPrimaryKeySelective(entity);
        Boolean flag = false;
        if(rs > 0){
            flag = true;
        }
        return RestVo.SUCCESS(flag);
    }

    public List<SysCoreUser> getUserInfoByCondition(UserBo bo){
        return userMapper.getUserInfoByCondition(bo);
    }


    /**
     * 删除用户信息
     * @param userId 目标用户的ID
     * @return
     */
    public RestVo<Boolean> remove(Long userId){
        int rs = userMapper.deleteByPrimaryKey(userId);
        Boolean flag = false;
        if(rs > 0){
            flag = true;
        }
        return RestVo.SUCCESS(flag);
    }

    /**
     * 取得当前用户角色已选择和未选择分页，并添加前台是否勾选标记
     * @param pageBo     分页参数
     * @return 分页结果
     */
    public RestVo<PageVo<RoleVo>> getRolePage(PageBo<RoleBo> pageBo){
        if(!CommUtils.isNull(pageBo.getPage()) && !CommUtils.isNull(pageBo.getSize())){
            PageHelper.startPage(pageBo.getPage(),pageBo.getSize());
        }
        List<RoleVo> datas = new ArrayList<>();
        List<SysCoreRole> entities = roleMapper.getAllRole();
        for(SysCoreRole entity : entities){
            RoleVo roleVo = new RoleVo();
            BeanUtils.copyProperties(entity, roleVo);
            SysCoreUserRoleLink userRoleLink = userRoleLinkMapper.getByUserIdAndRoleId(pageBo.getParam().getSelUid(),entity.getId());
            if(CommUtils.isNull(userRoleLink)  || userRoleLink.getStatus() == StatusEnum.DISABLED.getCode()){
                roleVo.setMark(Boolean.FALSE);
            }else{
                roleVo.setMark(Boolean.TRUE);
            }
            datas.add(roleVo);
        }
        return RestVo.SUCCESS(PageInfoUtil.trans2PageVo(entities,datas));
    }


    /**
     * 设定角色关联到用户
     * @param curUid   当前登录用户
     * @param selUid 目标用户的ID
     * @param roleId 角色ID列表
     * @return
     */
    @Transactional(propagation= Propagation.REQUIRED, rollbackFor = Exception.class)
    public RestVo<Map<Long,String>> createLinkedRole(Long curUid, Long selUid, Long roleId){
        SysCoreUserRoleLink userRoleLink = userRoleLinkMapper.getByUserIdAndRoleId(selUid,roleId);
        if(CommUtils.isNull(userRoleLink) || CommUtils.isNull(userRoleLink.getId())){
            userRoleLink = new SysCoreUserRoleLink();
            userRoleLink.setUserId(selUid);
            userRoleLink.setRoleId(roleId);
            userRoleLink.setStatus(StatusEnum.NORMAL.getCode());
            userRoleLink.setCreateBy(curUid);
            userRoleLink.setCreateAt(new Date());
            userRoleLinkMapper.insert(userRoleLink);
        }else {
            userRoleLink.setUpdateBy(curUid);
            userRoleLink.setUpdateAt(new Date());
            if(userRoleLink.getStatus() == StatusEnum.NORMAL.getCode()){
                userRoleLink.setStatus(StatusEnum.DISABLED.getCode());
            }else {
                userRoleLink.setStatus(StatusEnum.NORMAL.getCode());
            }
            userRoleLinkMapper.updateByPrimaryKeySelective(userRoleLink);
        }
        return RestVo.SUCCESS();
    }

    public SysCoreUser getByOpenId(String openId){
            return userMapper.getByOpenId(openId);
    }

}
