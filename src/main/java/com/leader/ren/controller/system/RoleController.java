package com.leader.ren.controller.system;


import com.leader.ren.common.dto.PageBo;
import com.leader.ren.component.BaseController;
import com.leader.ren.common.dto.PageVo;
import com.leader.ren.common.dto.RestVo;
import com.leader.ren.component.interceptor.AuthTarget;
import com.leader.ren.model.system.bo.MenuBo;
import com.leader.ren.model.system.bo.RoleBo;
import com.leader.ren.model.system.vo.MenuVo;
import com.leader.ren.model.system.vo.RoleVo;
import com.leader.ren.service.system.MenuService;
import com.leader.ren.service.system.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色
 * @Author: wangfang
 * date 2019/8/27
 */

@Slf4j
@AuthTarget(roles = "system-manager,system-role")
@RestController("system_role")
@Api(value = "system-role", description = "角色控制器")
@RequestMapping(value = "/system/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;

    @ApiOperation(value = "取得角色分页", notes="取得角色分页", produces = "application/json; charset=utf-8")
    @RequestMapping(value="page", method = RequestMethod.POST)
    public RestVo<PageVo<RoleVo>> page(@ApiParam(name = "bo", value = "数据", required = true) @RequestBody PageBo<RoleBo> bo){
        Long curUid = getUserId();
        return roleService.getPage(curUid, bo);
    }

    @ApiOperation(value = "创建角色信息", notes="创建角色信息",produces = "application/json; charset=utf-8")
    @RequestMapping(value="create", method = RequestMethod.POST)
    public RestVo<Boolean> create(@ApiParam(name = "bo", value = "数据", required = true) @RequestBody RoleBo bo){
//        writeLogService.write(WriteLogService.Functions.ROLE_MANAGER,new JSONObject().put("操作内容","创建角色信息"));
        Long curUid = getUserId();
        return roleService.create(curUid, bo);
    }


    @ApiOperation(value = "删除角色信息", notes="删除角色信息", produces = "application/json; charset=utf-8")
    @RequestMapping(value="remove", method = RequestMethod.DELETE)
    public RestVo<Boolean> remove(@ApiParam(name = "id", value = "主键", required = true) @RequestParam Long id){
//        writeLogService.write(WriteLogService.Functions.ROLE_MANAGER,new JSONObject().put("操作内容","删除角色信息").put("id:",id));
        Long curUid = getUserId();
        return roleService.remove(curUid, id);
    }

    @ApiOperation(value = "更新角色信息", notes="更新角色信息",produces = "application/json; charset=utf-8")
    @RequestMapping(value="update", method = RequestMethod.PUT)
    public RestVo<Boolean> update(@ApiParam(name = "id", value = "主键", required = true) @RequestParam Long id, @ApiParam(name = "bo", value = "数据", required = true) @RequestBody RoleBo bo){
        Long curUid = getUserId();
        bo.setId(id);
        return roleService.update(curUid, bo);
    }

    @ApiOperation(value = "取得角色信息", notes="取得角色信息", produces = "application/json; charset=utf-8")
    @RequestMapping(value="select", method = RequestMethod.GET)
    public RestVo<RoleVo> select(@ApiParam(name = "id", value = "主键", required = true) @RequestParam Long id){
        Long curUid = getUserId();
        return roleService.select(curUid, id);
    }

    @ApiOperation(value = "取得角色已关联菜单列表", notes="取得角色已关联菜单列表", produces = "application/json; charset=utf-8")
    @RequestMapping(value="selectLinkedMenu", method = RequestMethod.GET)
    public RestVo<List<MenuVo>> selectLinkedMenu(@ApiParam(name = "roleId", value = "角色主键", required = true) @RequestParam Long roleId){
        Long curUid = getUserId();
        return roleService.selectLinkedMenu(curUid, roleId);
    }

    @ApiOperation(value = "取得所有菜单列表", notes="取得所有菜单列表", produces = "application/json; charset=utf-8")
    @RequestMapping(value="selectAllMenu", method = RequestMethod.GET)
    public RestVo<List<MenuVo>> selectAllMenu(){
        Long curUid = getUserId();
        return menuService.getTree(curUid,  new MenuBo());
    }

    @ApiOperation(value = "批量设定菜单关联到角色", notes="批量设定菜单关联到角色", produces = "application/json; charset=utf-8")
    @RequestMapping(value="batchCreateLinkedMenu", method = RequestMethod.PUT)
    public RestVo batchCreateLinkedMenu(@ApiParam(name = "roleId", value = "角色主键", required = true) @RequestParam Long roleId, @ApiParam(name = "menuIds", value = "菜单列表", required = true) @RequestBody List<Long> menuIds){
        Long curUid = getUserId();
        return roleService.batchCreateLinkedMenu(curUid, roleId, menuIds);
    }

    @ApiOperation(value = "取得角色对应元素列表，并添加是否选中标记", notes="取得角色对应元素列表，并添加是否选中标记", produces = "application/json; charset=utf-8")
    @RequestMapping(value="selectElementAllMenu", method = RequestMethod.POST)
    public RestVo selectElementAllMenu(@ApiParam(name = "bo", value = "数据", required = true) @RequestBody PageBo<RoleBo> bo){
        return roleService.selectElementAllMenu(bo);
    }


    @ApiOperation(value = "从角色移除元素关联", notes="从角色移除元素关联(不分菜单)", produces = "application/json; charset=utf-8")
    @RequestMapping(value="removeLinkedElementAllMenu", method = RequestMethod.DELETE)
    public RestVo removeLinkedElementAllMenu(@ApiParam(name = "roleId", value = "角色主键", required = true) @RequestParam Long roleId, @ApiParam(name = "elementIds", value = "元素列表", required = true) @RequestBody List<Long> elementIds){
        Long curUid = getUserId();
        return roleService.removeLinkedElementAllMenu(curUid, roleId, elementIds);
    }

    @ApiOperation(value = "设定元素关联到角色", notes="设定元素关联到角色(不分菜单)", produces = "application/json; charset=utf-8"
    )
    @RequestMapping(value="createLinkedElementAllMenu", method = RequestMethod.PUT)
    public RestVo createLinkedElementAllMenu(@ApiParam(name = "roleId", value = "角色主键", required = true) @RequestParam Long roleId, @ApiParam(name = "elementIds", value = "元素列表", required = true) @RequestBody List<Long> elementIds){
        Long curUid = getUserId();
        return roleService.createLinkedElementAllMenu(curUid, roleId, elementIds);
    }

    @ApiOperation(value = "取得角色已关联菜单元素列表", notes="取得角色已关联菜单元素列表", produces = "application/json; charset=utf-8")
    @RequestMapping(value="selectLinkedElement", method = RequestMethod.GET)
    public RestVo selectLinkedElement(@ApiParam(name = "roleId", value = "角色主键", required = true) @RequestParam Long roleId, @ApiParam(name = "menuId", value = "菜单主键", required = true) @RequestParam Long menuId){
        Long curUid = getUserId();
        return roleService.selectLinkedElement(curUid, roleId, menuId);
    }


    @ApiOperation(value = "从角色移除菜单元素关联", notes="从角色移除菜单元素关联", produces = "application/json; charset=utf-8")
    @RequestMapping(value="removeLinkedElement", method = RequestMethod.DELETE)
    public RestVo removeLinkedElement(
            @ApiParam(name = "roleId", value = "角色主键", required = true) @RequestParam Long roleId,
            @ApiParam(name = "menuId", value = "菜单主键", required = true) @RequestParam Long menuId,
            @ApiParam(name = "elementIds", value = "菜单元素列表", required = true) @RequestBody List<Long> elementIds){
        Long curUid = getUserId();
        return roleService.removeLinkedElement(curUid, roleId, menuId, elementIds);
    }

    @ApiOperation(value = "设定菜单元素关联到角色", notes="设定菜单元素关联到角色", produces = "application/json; charset=utf-8")
    @RequestMapping(value="createLinkedElement", method = RequestMethod.PUT)
    public RestVo createLinkedElement(
            @ApiParam(name = "roleId", value = "角色主键", required = true) @RequestParam Long roleId,
            @ApiParam(name = "menuId", value = "菜单主键", required = true) @RequestParam Long menuId,
            @ApiParam(name = "elementIds", value = "菜单元素列表", required = true) @RequestBody List<Long> elementIds){
        Long curUid = getUserId();
        return roleService.createLinkedElement(curUid, roleId, menuId, elementIds);
    }

}
