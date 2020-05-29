package com.leader.ren.mapper.bigscreen;

import com.leader.ren.model.bigscreen.entity.EpidemicSample;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EpidemicSampleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(EpidemicSample record);

    int insertSelective(EpidemicSample record);

    EpidemicSample selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EpidemicSample record);

    int updateByPrimaryKey(EpidemicSample record);
}