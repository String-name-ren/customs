package com.leader.ren.mapper.bigscreen;

import com.leader.ren.model.bigscreen.entity.EpidemicPeoplePlane;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EpidemicPeoplePlaneMapper {
    int deleteByPrimaryKey(Long id);

    int insert(EpidemicPeoplePlane record);

    int insertSelective(EpidemicPeoplePlane record);

    EpidemicPeoplePlane selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EpidemicPeoplePlane record);

    int updateByPrimaryKey(EpidemicPeoplePlane record);

    List<EpidemicPeoplePlane> getPeoplePlaneData();
}