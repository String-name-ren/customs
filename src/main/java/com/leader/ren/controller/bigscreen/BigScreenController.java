package com.leader.ren.controller.bigscreen;

import com.leader.ren.common.dto.RestVo;
import com.leader.ren.model.bigscreen.LineVo;
import com.leader.ren.service.bigscreen.BigScreenStatisticsService;
import com.leader.ren.service.bigscreen.CustomsClearanceTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("big/screen")
public class BigScreenController {

    @Autowired
    private BigScreenStatisticsService importExportStatisticsService;

    @Autowired
    private CustomsClearanceTimeService customsClearanceTimeService;

    @PostMapping("getLeft1")
    public RestVo getLeft1(){
        return importExportStatisticsService.getImportExportStatistics();
    }


    @PostMapping("getLeft2")
    public RestVo getLeft2(){
        Map<String,Object> result = new HashMap<>();
        List<String> name = new ArrayList<>();
        List<String> value = new ArrayList<>();

        name.add("手机");
        name.add("ipad");
        name.add("电脑");
        value.add("100");
        value.add("200");
        value.add("180");

        result.put("name",name);
        result.put("value",value);
        return RestVo.SUCCESS(result);
    }


    @PostMapping("getBottom1")
    public RestVo getBottom1(){
        return customsClearanceTimeService.getClearanceTime();
    }

    @PostMapping("getBottom2")
    public RestVo getBottom2(){
        LineVo lineVo = new LineVo();
        List<String> title = new ArrayList<>();
        List<Map<String,String>> value = new ArrayList<>();

        title.add("企业1");
        title.add("企业2");
        title.add("企业3");
        title.add("企业4");


        Map<String,String> map1 = new HashMap<>();
        map1.put("name","企业1");
        map1.put("value","10");
        Map<String,String> map2 = new HashMap<>();
        map2.put("name","企业2");
        map2.put("value","20");
        Map<String,String> map3 = new HashMap<>();
        map3.put("name","企业3");
        map3.put("value","30");
        Map<String,String> map4 = new HashMap<>();
        map4.put("name","企业4");
        map4.put("value","10");


        value.add(map1);
        value.add(map2);
        value.add(map3);
        value.add(map4);


        lineVo.setTitle(title);
        lineVo.setValue(value);
        return RestVo.SUCCESS(lineVo);
    }


}
