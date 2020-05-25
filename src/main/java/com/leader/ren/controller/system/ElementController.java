package com.leader.ren.controller.system;



import com.leader.ren.common.dto.PageBo;
import com.leader.ren.component.BaseController;
import com.leader.ren.common.constant.RestMsg;
import com.leader.ren.common.dto.PageVo;
import com.leader.ren.common.dto.RestVo;
import com.leader.ren.component.interceptor.AuthTarget;
import com.leader.ren.model.system.bo.ElementBo;
import com.leader.ren.model.system.vo.ElementVo;
import com.leader.ren.service.system.ElementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 元素控制器
 *
 * @author 王泽志
 * date 2018/11/30 16:57
 */
@Slf4j
@AuthTarget(roles = "system-manager,system-element")
@RestController("system_element")
@Api(value = "system-element", description = "元素控制器")
@RequestMapping(value = "/system/element")
public class ElementController extends BaseController {

    @Autowired
    private ElementService elementService;

    @ResponseBody
    @ApiOperation(
            value = "取得元素分页", notes="取得元素分页",
            produces = "application/json; charset=utf-8"
    )
    @RequestMapping(value="page", method = RequestMethod.POST)
    public RestVo<PageVo<ElementVo>> page(
            @ApiParam(name = "bo", value = "数据", required = true) @RequestBody PageBo<ElementBo> bo
    ){

        try{
            Long curUid = getUserId();

            return elementService.getPage(curUid, bo);
        } catch (Exception e){
            log.error("取得元素分页异常：", e);
            return RestVo.FAIL(RestMsg.ERROR.getCode(),RestMsg.ERROR.getName());
        }
    }

    @ResponseBody
    @ApiOperation(
            value = "取得元素列表", notes="取得元素列表",
            produces = "application/json; charset=utf-8"
    )
    @RequestMapping(value="list", method = RequestMethod.GET)
    public RestVo<List<ElementVo>> list(){

        try{
            Long curUid = getUserId();

            return elementService.getList(curUid);
        } catch (Exception e){
            log.error("取得元素列表异常：", e);
            return RestVo.FAIL(RestMsg.ERROR.getCode(),RestMsg.ERROR.getName());
        }
    }

    @ResponseBody
    @ApiOperation(
            value = "创建元素信息", notes="创建元素信息",
            produces = "application/json; charset=utf-8"
    )
    @RequestMapping(value="create", method = RequestMethod.POST)
    public RestVo<Boolean> create(
            @ApiParam(name = "bo", value = "数据", required = true) @RequestBody ElementBo bo
    ){

        try{
            Long curUid = getUserId();

            return elementService.create(curUid, bo);
        } catch (Exception e){
            log.error("创建元素信息异常：", e);
            return RestVo.FAIL(RestMsg.ERROR.getCode(),RestMsg.ERROR.getName());
        }
    }

    @ResponseBody
    @ApiOperation(
            value = "删除元素信息", notes="删除元素信息",
            produces = "application/json; charset=utf-8"
    )
    @RequestMapping(value="remove", method = RequestMethod.DELETE)
    public RestVo<Boolean> remove(
            @ApiParam(name = "id", value = "主键", required = true) @RequestParam Long id
    ){

        try{
            Long curUid = getUserId();

            return elementService.remove(curUid, id);
        } catch (Exception e){
            log.error("删除元素信息异常：", e);
            return RestVo.FAIL(RestMsg.ERROR.getCode(),RestMsg.ERROR.getName());
        }

    }

    @ResponseBody
    @ApiOperation(
            value = "更新元素信息", notes="更新元素信息",
            produces = "application/json; charset=utf-8"
    )
    @RequestMapping(value="update", method = RequestMethod.PUT)
    public RestVo<Boolean> update(
            @ApiParam(name = "id", value = "主键", required = true) @RequestParam Long id,
            @ApiParam(name = "bo", value = "分页参数", required = true) @RequestBody ElementBo bo
    ){

        try{
            Long curUid = getUserId();

            bo.setId(id);
            return elementService.update(curUid, bo);
        } catch (Exception e){
            log.error("更新元素信息异常：", e);
            return RestVo.FAIL(RestMsg.ERROR.getCode(),RestMsg.ERROR.getName());
        }

    }

    @ResponseBody
    @ApiOperation(
            value = "取得元素信息", notes="取得元素信息",
            produces = "application/json; charset=utf-8"
    )
    @RequestMapping(value="select", method = RequestMethod.GET)
    public RestVo<ElementVo> select(
            @ApiParam(name = "id", value = "主键", required = true) @RequestParam Long id
    ){

        try{
            Long curUid = getUserId();

            return elementService.select(curUid, id);
        } catch (Exception e){
            log.error("取得元素信息异常：", e);
            return RestVo.FAIL(RestMsg.ERROR.getCode(),RestMsg.ERROR.getName());
        }

    }
}
