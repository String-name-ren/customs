package com.leader.ren.service.weChatService;

import com.leader.ren.common.dto.RestVo;
import com.leader.ren.common.utils.CommUtils;
import com.leader.ren.common.utils.MD5;
import com.leader.ren.common.utils.MyBeanUtils;
import com.leader.ren.mapper.system.SysCoreUserMapper;
import com.leader.ren.model.system.bo.WeChatUserBo;
import com.leader.ren.model.system.entity.SysCoreUser;
import com.leader.ren.model.system.vo.WeChatUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WeChatLoginService {


    @Value("${sys.security.salt}")
    private String salt;

    @Autowired
    private SysCoreUserMapper userMapper;

    public RestVo login(WeChatUserBo bo) {
        //TODO 登录校验 -- 校验成功 绑定 openId ,并且返回给我openID
        String username = bo.getUserName();
        String password = MD5.sign(bo.getPassword(), salt);
        SysCoreUser sysCoreUser = userMapper.selectByAuthInfo(username, password);
        if(CommUtils.isNull(sysCoreUser)){
            return RestVo.FAIL("用户名或密码错误！");
        }
        if(CommUtils.isNull(bo.getOpenId())){
            return RestVo.FAIL("无法获取用户权限！");
        }
        if(CommUtils.isNull(sysCoreUser.getOpenId())){
            //如果openId为空update
            SysCoreUser openUser = userMapper.getByOpenId(bo.getOpenId());
            if (CommUtils.isNotNull(openUser) && !sysCoreUser.getId().equals(openUser.getId())) {
                return RestVo.FAIL("用户已经绑定！");
            }
            sysCoreUser.setOpenId(bo.getOpenId());
            userMapper.updateByPrimaryKeySelective(sysCoreUser);
            WeChatUserVo userVo = new WeChatUserVo();
            MyBeanUtils.copyProperties(sysCoreUser,userVo);
            return RestVo.SUCCESS(userVo);
        }else{
            //判断是否一致不一致的话报错返回
            if(sysCoreUser.getOpenId().equals(bo.getOpenId())){
                WeChatUserVo userVo = new WeChatUserVo();
                MyBeanUtils.copyProperties(sysCoreUser,userVo);
                return RestVo.SUCCESS(userVo);
            }else{
                return RestVo.FAIL("该账号已绑定其他手机！");
            }
        }

       /* SysCoreUser openUser = userMapper.getByOpenId(bo.getOpenId());
        if (CommUtils.isNotNull(openUser) && !sysCoreUser.getId().equals(openUser.getId())) {
            openUser.setOpenId("");
            userMapper.updateByPrimaryKeySelective(openUser);
        }
        sysCoreUser.setOpenId(bo.getOpenId());
        userMapper.updateByPrimaryKeySelective(sysCoreUser);

        WeChatUserVo userVo = new WeChatUserVo();
        MyBeanUtils.copyProperties(sysCoreUser,userVo);
        return RestVo.SUCCESS(userVo);*/
    }
}
