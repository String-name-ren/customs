package com.leader.ren.mapper.bigscreen;

import com.leader.ren.model.bigscreen.entity.EpidemicPie;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EpidemicPieMapper {
    int deleteByPrimaryKey(Long id);

    int insert(EpidemicPie record);

    int insertSelective(EpidemicPie record);

    EpidemicPie selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EpidemicPie record);

    int updateByPrimaryKey(EpidemicPie record);
}