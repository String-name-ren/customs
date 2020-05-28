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
        List<BusinessData> list = businessDataMapper.getBusinessData();

        List<Map<String,Object>> result = new ArrayList<>();
        if(CommUtils.isNotNull(list)){
            for(BusinessData businessData : list){
                Map<String,Object> map = new HashMap<>();
                map.put("name",businessData.getName());
                map.put("value",businessData.getDataValue());
                result.add(map);
            }
        }
        //右边第二个
        return RestVo.SUCCESS(result);
    }


}
