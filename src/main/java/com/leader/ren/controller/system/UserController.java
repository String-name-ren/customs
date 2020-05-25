package com.leader.ren.controller.system;

import com.leader.ren.common.dto.PageBo;
import com.leader.ren.component.BaseController;
import com.leader.ren.component.config.CacheAsyncService;
import com.leader.ren.common.constant.RestMsg;
import com.leader.ren.common.jwt.IJWTInfo;
import com.leader.ren.common.dto.PageVo;
import com.leader.ren.common.dto.RestVo;
import com.leader.ren.component.interceptor.AuthTarget;
import com.leader.ren.model.system.bo.RoleBo;
import com.leader.ren.model.system.bo.UserBo;
import com.leader.ren.model.system.entity.SysCoreUser;
import com.leader.ren.model.system.vo.RoleVo;
import com.leader.ren.model.system.vo.UserVo;
import com.leader.ren.service.system.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 描述
 * @author: renpenghui
 * @date: 2019-08-27 16:52
 **/
@RestController
@RequestMapping("/system/user")
@Slf4j
public class UserController extends BaseController {

    @Autowired
    private CacheAsyncService cacheService;

    @Autowired
    private UserService userService;

    @GetMapping("get")
    public SysCoreUser get(){
        return userService.get();
    }

    @ApiOperation(value = "取得用户分页", notes = "取得用户分页", produces = "application/json; charset=utf-8")
    @RequestMapping(value = "page", method = RequestMethod.POST)
    @AuthTarget
    public RestVo<PageVo<UserVo>> page(@ApiParam(name = "bo", value = "数据", required = true) @RequestBody PageBo<UserBo> bo) {
        try {

            IJWTInfo user = this.getUser();
            return userService.getPage( bo);
        } catch (Exception e) {
            log.error("异常信息", e);
            return RestVo.FAIL(RestMsg.ERROR);
        }
    }

    @ApiOperation(value = "创建用户信息", notes = "创建用户信息", produces = "application/json; charset=utf-8")
    @RequestMapping(value = "create", method = RequestMethod.POST)
    @AuthTarget
    public RestVo<Boolean> create(@ApiParam(name = "bo", value = "数据", required = true) @RequestBody UserBo bo) {
        Long curUid = getUserId();
        Long userNameCount = userService.findUserNameCount(bo.getUsername());
        if (userNameCount > 0) {
            return RestVo.FAIL("用户名已存在");
        }
        userService.create(curUid, bo);
        return RestVo.SUCCESS(true);
    }

    @ApiOperation(value = "取得用户信息", notes = "取得用户信息", produces = "application/json; charset=utf-8")
    @RequestMapping(value = "select", method = RequestMethod.GET)
    @AuthTarget
    public RestVo<UserVo> select(@ApiParam(name = "id", value = "主键", required = true) @RequestParam Long id) {
        try {
            return userService.select(id);
        } catch (Exception e) {
            log.error("异常信息", e);
            return RestVo.FAIL(RestMsg.ERROR);
        }
    }

    @ApiOperation(value = "更新用户信息", notes = "更新用户信息", produces = "application/json; charset=utf-8")
    @RequestMapping(value = "update", method = RequestMethod.PUT)
    @AuthTarget
    public RestVo<Boolean> update(@ApiParam(name = "id", value = "主键", required = true) @RequestParam Long id, @ApiParam(name = "bo", value = "数据", required = true) @RequestBody UserBo bo) {
        try {
            Long curUid = getUserId();
            bo.setId(id);
            return userService.update(curUid, bo);
        } catch (Exception e) {
            log.error("异常信息", e);
            return RestVo.FAIL(RestMsg.ERROR);
        }
    }

    @ApiOperation(value = "删除用户信息", notes = "删除用户信息", produces = "application/json; charset=utf-8")
    @RequestMapping(value = "remove", method = RequestMethod.DELETE)
    @AuthTarget
    public RestVo<Boolean> remove(@ApiParam(name = "id", value = "主键", required = true) @RequestParam Long id) {
        try {
            return userService.remove(id);
        } catch (Exception e) {
            log.error("异常信息", e);
            return RestVo.FAIL(RestMsg.ERROR);
        }
    }

    @ApiOperation(value = "取得当前用户角色已选择和未选择分页，并添加前台是否勾选标记", notes = "取得当前用户角色已选择和未选择分页，并添加前台是否勾选标记", produces = "application/json; charset=utf-8")
    @RequestMapping(value = "rolePage", method = RequestMethod.POST)
    @AuthTarget
    public RestVo<PageVo<RoleVo>> rolePage(@ApiParam(name = "bo", value = "数据", required = true) @RequestBody PageBo<RoleBo> bo) {
        try {
            return userService.getRolePage(bo);
        } catch (Exception e) {
            log.error("异常信息", e);
            return RestVo.FAIL(e.getMessage());
        }
    }

    @ApiOperation(value = "设定角色关联到用户", notes = "设定角色关联到用户", produces = "application/json; charset=utf-8")
    @RequestMapping(value = "createLinkedRole", method = RequestMethod.PUT)
    @AuthTarget
    public RestVo createLinkedRole(@ApiParam(name = "selUid", value = "用户主键", required = true) @RequestParam Long selUid,
                                   @ApiParam(name = "roleId", value = "角色id", required = true) @RequestParam Long roleId) {
        try {
            Long curUid = getUserId();
            return userService.createLinkedRole(curUid, selUid, roleId);
        } catch (Exception e) {
            log.error("异常信息", e);
            return RestVo.FAIL(RestMsg.ERROR);
        }
    }


    @ApiOperation(value = "设定角色关联到用户", notes = "设定角色关联到用户", produces = "application/json; charset=utf-8")
    @RequestMapping(value = "removeLinkedRole", method = RequestMethod.DELETE)
    @AuthTarget
    public RestVo removeLinkedRole(@ApiParam(name = "selUid", value = "用户主键", required = true) @RequestParam Long selUid,
                                   @ApiParam(name = "roleId", value = "角色id", required = true) @RequestParam Long roleId) {
        try {
            Long curUid = getUserId();
            return userService.createLinkedRole(curUid, selUid, roleId);
        } catch (Exception e) {
            log.error("异常信息", e);
            return RestVo.FAIL(RestMsg.ERROR);
        }
    }

}
