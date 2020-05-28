package com.leader.ren.mapper.bigscreen;

import com.leader.ren.model.bigscreen.entity.CustomsClearanceTime;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustomsClearanceTimeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CustomsClearanceTime record);

    int insertSelective(CustomsClearanceTime record);

    CustomsClearanceTime selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomsClearanceTime record);

    int updateByPrimaryKey(CustomsClearanceTime record);

    List<CustomsClearanceTime>  getClearanceTime();
}