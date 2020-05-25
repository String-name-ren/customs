package com.leader.ren.mapper.system;


import com.leader.ren.model.system.bo.UserBo;
import com.leader.ren.model.system.entity.SysCoreUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysCoreUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysCoreUser record);

    int insertSelective(SysCoreUser record);

    SysCoreUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysCoreUser record);

    int updateByPrimaryKey(SysCoreUser record);

    /**
     * 取得登录信息
     * @param username
     * @param password
     * @return
     */
    SysCoreUser selectByAuthInfo(@Param("username") String username, @Param("password") String password);

    List<SysCoreUser> selectSearchData(UserBo userBo);

    Long selectNameCount(@Param("params") Map params);

    List<SysCoreUser> getUserInfoByCondition(UserBo bo);

    SysCoreUser getByOpenId(@Param("openId") String openId);

}