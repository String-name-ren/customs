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
        List<String> echarts41 = new ArrayList<>();
        List<String> echarts42 = new ArrayList<>();

        List<String> bottom31 = new ArrayList<>();
        List<String> echarts511 = new ArrayList<>();
        List<String> echarts512 = new ArrayList<>();
        List<String> bottom32 = new ArrayList<>();
        List<String> echarts521 = new ArrayList<>();
        List<String> echarts522 = new ArrayList<>();

        List<String> bottom4 = new ArrayList<>();

        List<String> right2 = new ArrayList<>();
        List<String> echarts71 = new ArrayList<>();
        List<String> echarts72 = new ArrayList<>();

        if(CommUtils.isNotNull(list)){
            for(BigScreenStatistics statistics : list){
                title.add(statistics.getMonth());
                data1.add(statistics.getBillCountTotal());
                data2.add(statistics.getCargoValueTotal());
                data3.add(statistics.getCargoValueIn());
                left2.add(statistics.getTax());

                echarts41.add(statistics.getPeopleIn());
                echarts42.add(statistics.getPeopleOut());
                bottom2.add(statistics.getPeopleTotal());

                //第五个
                bottom31.add(statistics.getPlaneInTotal());
                echarts511.add(statistics.getPlaneBusIn());
                echarts512.add(statistics.getPlaneCargoIn());
                bottom32.add(statistics.getPlaneOutTotal());
                echarts521.add(statistics.getPlaneBusOut());
                echarts522.add(statistics.getPlaneCargoOut());


                bottom4.add(statistics.getExpressIn());
                right2.add(statistics.getClueTotal());
                echarts71.add(statistics.getClueTrade());
                echarts72.add(statistics.getClueNonTrade());
            }
        }


        result.put("title",title);//X轴名称
        //1
        result.put("echarts11",data1);
        result.put("echarts12",data2);
        result.put("echarts13",data3);
        //2
        result.put("echarts2",left2);
        //4
        result.put("echarts4",bottom2); //进出境总数
        result.put("echarts41",echarts41);//进境
        result.put("echarts42",echarts42);//出境
        //5
        result.put("echarts51",bottom31);//进境飞机总数
        result.put("echarts511",echarts511);//进境客机
        result.put("echarts512",echarts512);//进境货机

        result.put("echarts52",bottom32);//出境飞机总数
        result.put("echarts521",echarts521);//出境客机
        result.put("echarts522",echarts522);//出境货机

//        //8
//        result.put("echarts8",bottom4);
        //7
        result.put("echarts7",right2);//总数
        result.put("echarts71",echarts71);//贸易
        result.put("echarts72",echarts72);//非贸易
        return RestVo.SUCCESS(result);
    }

}
