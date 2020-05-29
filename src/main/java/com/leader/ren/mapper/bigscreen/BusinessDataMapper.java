package com.leader.ren.mapper.bigscreen;

import com.leader.ren.model.bigscreen.entity.BusinessData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BusinessDataMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BusinessData record);

    int insertSelective(BusinessData record);

    BusinessData selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BusinessData record);

    int updateByPrimaryKey(BusinessData record);

    List<BusinessData> getBusinessData(Integer type);
}