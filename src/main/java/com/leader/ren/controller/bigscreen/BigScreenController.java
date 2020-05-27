package com.leader.ren.controller.bigscreen;

import com.leader.ren.common.dto.RestVo;
import com.leader.ren.model.bigscreen.LineVo;
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

    @PostMapping("getLeft1")
    public RestVo getLeft1(){
        LineVo lineVo = new LineVo();
        List<String> title = new ArrayList<>();
        List<Map<String,String>> value = new ArrayList<>();

        title.add("品名1");
        title.add("品名2");
        title.add("品名3");
        title.add("品名4");
        title.add("品名5");

        Map<String,String> map1 = new HashMap<>();
        map1.put("name","品名1");
        map1.put("value","200");
        Map<String,String> map2 = new HashMap<>();
        map2.put("name","品名2");
        map2.put("value","500");
        Map<String,String> map3 = new HashMap<>();
        map3.put("name","品名3");
        map3.put("value","300");
        Map<String,String> map4 = new HashMap<>();
        map4.put("name","品名4");
        map4.put("value","1000");
        Map<String,String> map5 = new HashMap<>();
        map5.put("name","品名5");
        map5.put("value","1000");

        value.add(map1);
        value.add(map2);
        value.add(map3);
        value.add(map4);
        value.add(map5);

        lineVo.setTitle(title);
        lineVo.setValue(value);
        return RestVo.SUCCESS(lineVo);
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


}
