package com.leader.ren.mapper.bigscreen;

import com.leader.ren.model.bigscreen.entity.EpidemicScore;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EpidemicScoreMapper {
    int deleteByPrimaryKey(Long id);

    int insert(EpidemicScore record);

    int insertSelective(EpidemicScore record);

    EpidemicScore selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EpidemicScore record);

    int updateByPrimaryKey(EpidemicScore record);

    List<EpidemicScore> getScoreData();
}