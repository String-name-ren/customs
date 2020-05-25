package com.leader.ren.controller.system;


import com.leader.ren.common.dto.PageBo;
import com.leader.ren.component.BaseController;
import com.leader.ren.common.constant.RestMsg;
import com.leader.ren.common.dto.PageVo;
import com.leader.ren.common.dto.RestVo;
import com.leader.ren.component.interceptor.AuthTarget;
import com.leader.ren.model.system.bo.MenuBo;
import com.leader.ren.model.system.vo.MenuVo;
import com.leader.ren.service.system.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单控制器
 *
 * @author 王泽志
 * date 2018/11/30 16:57
 */
@Slf4j
@AuthTarget(roles = "system-manager,system-menu")
@RestController("system_menu")
@Api(value = "system-menu", description = "菜单控制器")
@RequestMapping(value = "/system/menu")
public class MenuController extends BaseController {

	@Autowired
	private MenuService menuService;

	@ResponseBody
	@ApiOperation(
			value = "取得菜单树", notes="取得菜单树",
			produces = "application/json; charset=utf-8"
	)
	@RequestMapping(value="tree", method = RequestMethod.POST)
	public RestVo<List<MenuVo>> tree(
			@ApiParam(name = "bo", value = "数据", required = true) @RequestBody MenuBo bo
	){
		try{
			Long curUid = getUserId();

			return menuService.getTree(curUid, bo);
		} catch (Exception e){
			log.error("取得菜单树异常：", e);
			return RestVo.FAIL(RestMsg.ERROR.getCode(),RestMsg.ERROR.getName());
		}

	}

	@ResponseBody
	@ApiOperation(
			value = "取得菜单分页", notes="取得菜单分页",
			produces = "application/json; charset=utf-8"
	)
	@RequestMapping(value="page", method = RequestMethod.POST)
	public RestVo<PageVo<MenuVo>> page(
			@ApiParam(name = "bo", value = "数据", required = true) @RequestBody PageBo<MenuBo> bo
	){
		try{
			Long curUid = getUserId();

			return menuService.getPage(curUid, bo);
		} catch (Exception e){
			log.error("取得菜单分页异常：", e);
			return RestVo.FAIL(RestMsg.ERROR.getCode(),RestMsg.ERROR.getName());
		}

	}

	@ResponseBody
	@ApiOperation(
			value = "取得菜单列表", notes="取得菜单列表",
			produces = "application/json; charset=utf-8"
	)
	@RequestMapping(value="list", method = RequestMethod.GET)
	public RestVo<List<MenuVo>> list(){
		try{
			Long curUid = getUserId();

			return menuService.getList(curUid);
		} catch (Exception e){
			log.error("取得菜单列表异常：", e);
			return RestVo.FAIL(RestMsg.ERROR.getCode(),RestMsg.ERROR.getName());
		}

	}

	@ResponseBody
	@ApiOperation(
			value = "创建菜单信息", notes="创建菜单信息",
			produces = "application/json; charset=utf-8"
	)
	@RequestMapping(value="create", method = RequestMethod.POST)
	public RestVo<Boolean> create(
			@ApiParam(name = "bo", value = "数据", required = true) @RequestBody MenuBo bo
	){
		try{
			Long curUid = getUserId();

			return menuService.create(curUid, bo);
		} catch (Exception e){
			log.error("创建菜单信息异常：", e);
			return RestVo.FAIL(RestMsg.ERROR.getCode(),RestMsg.ERROR.getName());
		}

	}

	@ResponseBody
	@ApiOperation(
			value = "删除菜单信息", notes="删除菜单信息",
			produces = "application/json; charset=utf-8"
	)
	@RequestMapping(value="remove", method = RequestMethod.DELETE)
	public RestVo<Boolean> remove(
			@ApiParam(name = "id", value = "主键", required = true) @RequestParam Long id
	){
		try{
			Long curUid = getUserId();

			return menuService.remove(curUid, id);
		} catch (Exception e){
			log.error("删除菜单信息异常：", e);
			return RestVo.FAIL(RestMsg.ERROR.getCode(),RestMsg.ERROR.getName());
		}

	}

	@ResponseBody
	@ApiOperation(
			value = "更新菜单信息", notes="更新菜单信息",
			produces = "application/json; charset=utf-8"
	)
	@RequestMapping(value="update", method = RequestMethod.PUT)
	public RestVo<Boolean> update(
			@ApiParam(name = "id", value = "主键", required = true) @RequestParam Long id,
			@ApiParam(name = "bo", value = "数据", required = true) @RequestBody MenuBo bo
	){
		try{
			Long curUid = getUserId();

			bo.setId(id);
			return menuService.update(curUid, bo);
		} catch (Exception e){
			log.error("更新菜单信息异常：", e);
			return RestVo.FAIL(RestMsg.ERROR.getCode(),RestMsg.ERROR.getName());
		}
	}

	@ResponseBody
	@ApiOperation(
			value = "取得菜单信息", notes="取得菜单信息",
			produces = "application/json; charset=utf-8"
	)
	@RequestMapping(value="select", method = RequestMethod.GET)
	public RestVo<MenuVo> select(
			@ApiParam(name = "id", value = "主键", required = true) @RequestParam Long id
	){
		try{
			Long curUid = getUserId();

			return menuService.select(curUid, id);
		} catch (Exception e){
			log.error("取得菜单信息异常：", e);
			return RestVo.FAIL(RestMsg.ERROR.getCode(),RestMsg.ERROR.getName());
		}
	}
}
