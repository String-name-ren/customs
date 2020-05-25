package com.leader.ren.mapper.system;

import com.leader.ren.model.system.entity.SysCoreUserRoleLink;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysCoreUserRoleLinkMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysCoreUserRoleLink record);

    int insertSelective(SysCoreUserRoleLink record);

    SysCoreUserRoleLink selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysCoreUserRoleLink record);

    int updateByPrimaryKey(SysCoreUserRoleLink record);

    SysCoreUserRoleLink getByUserIdAndRoleId(@Param("userId") Long userId, @Param("roleId") Long roleId);

}