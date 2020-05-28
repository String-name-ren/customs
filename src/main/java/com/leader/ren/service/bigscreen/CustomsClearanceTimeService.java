package com.leader.ren.service.bigscreen;

import com.leader.ren.common.dto.RestVo;
import com.leader.ren.common.utils.CommUtils;
import com.leader.ren.mapper.bigscreen.CustomsClearanceTimeMapper;
import com.leader.ren.model.bigscreen.entity.CustomsClearanceTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CustomsClearanceTimeService {

    @Autowired
    private CustomsClearanceTimeMapper customsClearanceTimeMapper;


    public RestVo getClearanceTime(){
        List<CustomsClearanceTime> list = customsClearanceTimeMapper.getClearanceTime();

        Map<String,Object> result = new HashMap<>();

        List<String> title = new ArrayList<>();
        List<String> data = new ArrayList<>();

        if(CommUtils.isNotNull(list)){
            for(CustomsClearanceTime clearanceTime : list){
                title.add(clearanceTime.getMonth());
                data.add(clearanceTime.getTime());
            }
        }

        //底部第一个
        result.put("title",title);
        result.put("data",data);
        return RestVo.SUCCESS(result);
    }

}
