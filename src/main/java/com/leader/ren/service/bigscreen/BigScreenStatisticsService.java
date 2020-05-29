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

        List<String> right2 = new ArrayList<>();

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
                right2.add(statistics.getClueTotal());
            }
        }


        result.put("title",title);
        //1
        result.put("echarts11",data1);
        result.put("echarts12",data2);
        result.put("echarts13",data3);
        //2
        result.put("echarts2",left2);
        //4
        result.put("echarts4",bottom2);
        //5
        result.put("echarts51",bottom31);
        result.put("echarts52",bottom32);
//        //8
//        result.put("echarts8",bottom4);
        //7
        result.put("echarts7",right2);
        return RestVo.SUCCESS(result);
    }

}
