package com.leader.ren.service.bigscreen;

import com.leader.ren.common.dto.RestVo;
import com.leader.ren.common.utils.CommUtils;
import com.leader.ren.mapper.bigscreen.BigScreenStatisticsMapper;
import com.leader.ren.model.bigscreen.entity.BigScreenStatistics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class BigScreenStatisticsService {

    @Autowired
    private BigScreenStatisticsMapper bigScreenStatisticsMapper;

    public RestVo getImportExportStatistics(){

        List<BigScreenStatistics> list = bigScreenStatisticsMapper.getStatistics();

        Map<String,Object> result = new HashMap<>();

        List<String> title = new ArrayList<>();
        List<String> data1 = new ArrayList<>();
        List<String> data2 = new ArrayList<>();
        List<String> data3 = new ArrayList<>();

        List<String> left2 = new ArrayList<>();

        List<String> bottom2 = new ArrayList<>();

        List<String> bottom31 = new ArrayList<>();
        List<String> bottom32 = new ArrayList<>();

        List<String> bottom4 = new ArrayList<>();

        if(CommUtils.isNotNull(list)){
            for(BigScreenStatistics statistics : list){
                title.add(statistics.getMonth());
                data1.add(statistics.getBillCountTotal());
                data2.add(statistics.getCargoValueTotal());
                data3.add(statistics.getCargoValueIn());
                left2.add(statistics.getTax());
                bottom2.add(statistics.getPeopleTotal());
                bottom31.add(statistics.getPlaneInTotal());
                bottom32.add(statistics.getPlaneOutTotal());
                bottom4.add(statistics.getExpressIn());
            }
        }


        result.put("title",title);
        //左一显示
        result.put("data1",data1);
        result.put("data2",data2);
        result.put("data3",data3);
        //左二显示
        result.put("left2",left2);
        //底二显示
        result.put("bottom2",bottom2);
        //底三显示
        result.put("bottom31",bottom31);
        result.put("bottom32",bottom32);
        //底四显示
        result.put("bottom4",bottom4);
        return RestVo.SUCCESS(result);
    }

}
