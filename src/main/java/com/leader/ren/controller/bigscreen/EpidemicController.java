package com.leader.ren.controller.bigscreen;

import com.leader.ren.common.dto.RestVo;
import com.leader.ren.service.bigscreen.EpidemicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("epidemic")
@RestController
public class EpidemicController {

    @Autowired
    private EpidemicService epidemicService;

    @PostMapping("getLineData")
    public RestVo getLineData(){
        return epidemicService.getLineData();
    }


    @PostMapping("getPieData")
    public RestVo getPieData(){
        return epidemicService.getPieData();
    }



}
