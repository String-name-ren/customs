package com.leader.ren.controller.bigscreen;

import com.leader.ren.common.dto.RestVo;
import com.leader.ren.service.bigscreen.BigScreenStatisticsService;
import com.leader.ren.service.bigscreen.BusinessDataService;
import com.leader.ren.service.bigscreen.CustomsClearanceTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("big/screen")
public class BigScreenController {

    @Autowired
    private BigScreenStatisticsService importExportStatisticsService;

    @Autowired
    private CustomsClearanceTimeService customsClearanceTimeService;

    @Autowired
    private BusinessDataService businessDataService;

    @PostMapping("getLineData")
    public RestVo getLineData(){
        return importExportStatisticsService.getImportExportStatistics();
    }

    @PostMapping("getLineData2")
    public RestVo getLineData2(){
        return customsClearanceTimeService.getClearanceTime();
    }

    @PostMapping("getPieData")
    public RestVo getPieData(){
        return businessDataService.getBusinessData();
    }

}
