package com.leader.ren.mapper.system;


import com.leader.ren.model.system.entity.SysCoreRelation;

public interface SysCoreRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysCoreRelation record);

    int insertSelective(SysCoreRelation record);

    SysCoreRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysCoreRelation record);

    int updateByPrimaryKey(SysCoreRelation record);
}