package com.leader.ren.mapper.system;


import com.leader.ren.model.system.entity.SysDictionary;

import java.util.List;

public interface SysDictionaryMapper {
    int deleteByPrimaryKey(Long dicId);

    int insert(SysDictionary record);

    int insertSelective(SysDictionary record);

    SysDictionary selectByPrimaryKey(Long dicId);

    int updateByPrimaryKeySelective(SysDictionary record);

    int updateByPrimaryKey(SysDictionary record);

    List<SysDictionary> getList(String dicCode);

    int updateActive(String dicCode);

    SysDictionary getActiveDicByCode(String dicCode);
}