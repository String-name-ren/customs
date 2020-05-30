package com.leader.ren.service.bigscreen;

import com.leader.ren.common.dto.RestVo;
import com.leader.ren.common.utils.CommUtils;
import com.leader.ren.mapper.bigscreen.BusinessDataMapper;
import com.leader.ren.model.bigscreen.entity.BusinessData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class BusinessDataService {

    @Autowired
    private BusinessDataMapper businessDataMapper;

    public RestVo getBusinessData(){

        List<List<Map<String,Object>>> result = new ArrayList<>();


        //2.6.2进口
        List<Map<String,Object>> index1 = new ArrayList<>();
        List<BusinessData> list1 = businessDataMapper.getBusinessData(2);
        if(CommUtils.isNotNull(list1)){
            for(BusinessData businessData : list1){
                Map<String,Object> map = new HashMap<>();
                map.put("name",businessData.getName());
                map.put("value",businessData.getDataValue());
                index1.add(map);
            }
        }
        //2.6.2出口
        List<Map<String,Object>> index2 = new ArrayList<>();
        List<BusinessData> list2 = businessDataMapper.getBusinessData(3);
        if(CommUtils.isNotNull(list2)){
            for(BusinessData businessData : list2){
                Map<String,Object> map = new HashMap<>();
                map.put("name",businessData.getName());
                map.put("value",businessData.getDataValue());
                index2.add(map);
            }
        }

        //2.8
        List<Map<String,Object>> index0 = new ArrayList<>();
        List<BusinessData> list0 = businessDataMapper.getBusinessData(1);
        if(CommUtils.isNotNull(list0)){
            for(BusinessData businessData : list0){
                Map<String,Object> map = new HashMap<>();
                map.put("name",businessData.getName());
                map.put("value",businessData.getDataValue());
                index0.add(map);
            }
        }

        result.add(index1);
        result.add(index2);
        result.add(index0);
        return RestVo.SUCCESS(result);
    }


}
