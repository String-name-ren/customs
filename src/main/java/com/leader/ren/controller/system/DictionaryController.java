package com.leader.ren.controller.system;

import com.leader.ren.common.dto.RestVo;
import com.leader.ren.model.system.entity.SysDictionary;
import com.leader.ren.service.system.DictionaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/dictionary")
@Api(description = "字典控制器")
@Slf4j
public class DictionaryController {

    @Autowired(required = false)
    private DictionaryService dictionaryService;

    @ApiOperation("根据dicCode获取字典列表")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public RestVo<List<SysDictionary>> list(@RequestParam(value = "dicCode", required = true) String dicCode){
        RestVo<List<SysDictionary>> restVo = new RestVo<>();
        restVo.setData(dictionaryService.getList(dicCode));
        return restVo;
    }

    @ApiOperation("设置各字典组的默认值")
    @RequestMapping(value = "/setting",method = RequestMethod.GET)
    public RestVo setting(@RequestParam(value = "timeDiff", required = true) Long timeDiff,
                          @RequestParam(value = "activeIp", required = true) Long activeIp,
                          @RequestParam(value = "clickIp", required = true) Long clickIp){
        dictionaryService.setting(timeDiff, activeIp, clickIp);
        return RestVo.SUCCESS();
    }
}
