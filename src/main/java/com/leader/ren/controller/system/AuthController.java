package com.leader.ren.controller.system;


import com.leader.ren.component.BaseController;
import com.leader.ren.common.constant.RestMsg;
import com.leader.ren.common.dto.RestVo;
import com.leader.ren.component.interceptor.AuthTarget;
import com.leader.ren.model.system.bo.AuthBo;
import com.leader.ren.model.system.bo.PassWordBo;
import com.leader.ren.model.system.vo.AuthVo;
import com.leader.ren.model.system.vo.MenuVo;
import com.leader.ren.service.system.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 描述
 *
 * @author: renpenghui
 * @date: 2019-08-27 17:36
 **/
@Slf4j
@RestController
@Api(value = "core-auth", description = "用户认证控制器")
@RequestMapping(value = "/system/auth")
public class AuthController extends BaseController {

    // 令牌头
    @Value("${jwt.user.token-header:}")
    private String tokenHeader;
    // 公钥
    @Value("${sys.security.publicKey:}")
    private String publicKey;

    @Resource
    private AuthService authService;

    @Autowired(required = false)
    protected HttpServletRequest request;

    @Autowired(required = false)
    protected HttpServletResponse response;

    @ApiOperation(value = "图片验证码生成", notes = "验证码图片生成（BASE64格式字符串）",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "codeMaker", method = RequestMethod.GET)
    public RestVo<AuthVo> codeMaker() {
        try {
            return authService.codeMaker();
        } catch (Exception e) {
            log.error("异常信息", e);
            return RestVo.FAIL(RestMsg.IMAGE_GENERATE_ERROR);
        }
    }

    @ResponseBody
    @RequestMapping(value = "codeImgLogin", method = RequestMethod.POST)
    @ApiOperation(value = "用户登录", notes = "用户登录（密码登录、图片验证码）",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestVo<AuthVo> codeImgLogin(@ApiParam(name = "bo", value = "对象参数", required = true) @RequestBody AuthBo bo) {
        try {
            return authService.codeImgLogin(bo);
        } catch (Exception e) {
            log.error("异常信息", e);
            return RestVo.FAIL(RestMsg.LOGIN_FAIL);
        }
    }


    @ResponseBody
    @ApiOperation(value = "用户信息", notes = "用户信息取得",produces = "application/json; charset=utf-8")
    @RequestMapping(value = "infos", method = RequestMethod.GET)
    public RestVo<AuthVo> infos(@ApiParam(name = "token", value = "用户令牌", required = true) @RequestHeader("token") String token) {
        log.info("/auth/infos -> 用户信息########token:" + token);
        try {
            token = StringUtils.isEmpty(token) ? getToken() : token;
            return authService.getInfos(token);
        } catch (Exception e) {
            log.error("异常信息", e);
            return RestVo.FAIL(RestMsg.ERROR);
        }
    }

    public String getToken() {
//        String token = (String) BaseContext.get("token");
        String token = null;
        if (StringUtils.isEmpty(token)) {
            token = request.getHeader(tokenHeader);
        }

        if (StringUtils.isEmpty(token)) {
            token = request.getParameter(tokenHeader);
        }

        return token;
    }



    @ResponseBody
    @ApiOperation(value = "用户菜单", notes = "用户菜单取得",produces = "application/json; charset=utf-8")
    @RequestMapping(value = "menuList", method = RequestMethod.GET)
    @AuthTarget
    public RestVo<List<MenuVo>> menuList(@ApiParam(name = "token", value = "用户令牌", required = true) @RequestHeader("token") String token) {
        log.info("/auth/logout -> 用户菜单########token:" + token);
        try {
            return authService.getMenuList();
        } catch (Exception e) {
            log.error("异常信息", e);
            return RestVo.FAIL(RestMsg.ERROR);
        }
    }


    @ApiOperation(value = "修改用户密码", notes = "修改用户密码", produces = "application/json; charset=utf-8" )
    @RequestMapping(value = "updatePassWord", method = RequestMethod.PUT)
    public RestVo<Boolean> updatePassWord(@ApiParam(name = "bo", value = "修改密码对象参数", required = true) @RequestBody PassWordBo bo) {
        try {
            Long curUid = getUserId();
            return authService.updatePassWord(curUid, bo.getPassWord(), bo.getNewPassWord(), this.getToken());
        } catch (Exception e) {
            log.error("修改用户密码异常", e);//sonar修复 by 付栓 20190509
            return RestVo.FAIL(RestMsg.ERROR);
        }
    }

    @ApiOperation(value = "获取元素权限", notes = "获取元素权限", produces = "application/json; charset=utf-8" )
    @RequestMapping(value = "getElementPermission", method = RequestMethod.POST)
    public RestVo getElementPermission(@RequestBody List<String> elementCode) {
        try {
            Long curUid = getUserId();
            return authService.getElementPermission(curUid, elementCode);
        } catch (Exception e) {
            log.error("修改用户密码异常", e);//sonar修复 by 付栓 20190509
            return RestVo.FAIL(RestMsg.ERROR);
        }
    }

}
