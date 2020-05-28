package com.leader.ren.mapper.bigscreen;

import com.leader.ren.model.bigscreen.entity.BigScreenStatistics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BigScreenStatisticsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BigScreenStatistics record);

    int insertSelective(BigScreenStatistics record);

    BigScreenStatistics selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BigScreenStatistics record);

    int updateByPrimaryKey(BigScreenStatistics record);

    List<BigScreenStatistics> getStatistics();
}