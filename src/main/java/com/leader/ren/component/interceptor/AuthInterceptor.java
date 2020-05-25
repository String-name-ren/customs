package com.leader.ren.component.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.leader.ren.component.config.CacheAsyncService;
import com.leader.ren.common.constant.Common;
import com.leader.ren.common.constant.RestMsg;
import com.leader.ren.common.utils.BaseContext;
import com.leader.ren.common.utils.CommUtils;
import com.leader.ren.common.dto.RestVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * 用户登录及权限验证
 */
@Slf4j
@Component
@ControllerAdvice
public class AuthInterceptor extends HandlerInterceptorAdapter {
    // 登录页面
    @Value("${login.page.uri}")
    private String loginPageUri;

    // 令牌参数名称
    @Value("${jwt.user.token-header}")
    private String tokenName;

    /**
     * 令牌过期时间
     */
    @Value("${jwt.user.token-expire}")
    private Integer tokenExpire;

    @Autowired
    CacheAsyncService cacheAsyncService;

    /**
     * 前处理
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        if (request.getHeader(HttpHeaders.ORIGIN) != null) {
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Allow-Headers:", "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN,token,username,client");
            response.addHeader("Access-Control-Allow-Methods", "*");
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Expose-Headers", "*");
            response.addHeader("Access-Control-Max-Age", "18000L");
        }

        if (!handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            return true;
        }

        final HandlerMethod handlerMethod = (HandlerMethod) handler;
        final Method method = handlerMethod.getMethod();
        final Class<?> clazz = method.getDeclaringClass();

        // 从请求头取得令牌
        String token = request.getHeader(tokenName);

        // 从参数中取得令牌
        if(StringUtils.isEmpty(token)){ // 从参数取得令牌
            token = request.getParameter(tokenName);
        }

        log.info("登录IP：{}, 令牌：{}" , getClientIp(request), token);

        // 允许访问标记
        Boolean accessFlag = true;

        // 取得控制器前缀路径

        // 出现注解则需要验证用户权限
        if (clazz.isAnnotationPresent(AuthTarget.class)
                || method.isAnnotationPresent(AuthTarget.class)) {

            String userIdKey = Common.USER_LOGIN_HY_ID + token;
            String userId = (String)cacheAsyncService.get(userIdKey);
            if(StringUtils.isEmpty(token)
                    || CommUtils.isNull(userId)){
                accessFlag = false;
            }else{
                BaseContext.set("token", token);

                long startTime = System.currentTimeMillis();
                // 服务端权限验证
                Boolean elemenAllFalg=false;
                Boolean elementFalg=false;
                String requestURI = request.getRequestURI();
                List<Serializable> elementsAll = cacheAsyncService.getList("user:system:elementsAll");
                if (!CommUtils.isNull(elementsAll)){
                    for(Serializable str:elementsAll){
                        if(requestURI.indexOf(String.valueOf(str))>-1){
                            elemenAllFalg=true;
                        }
                    }
                }
                if(elemenAllFalg){
                    List<Serializable> elementsList = cacheAsyncService.getList("user:system:elementsUser:" + userId);
                    if (!CommUtils.isNull(elementsList)){
                        for(Serializable elementVo:elementsList){
                            if(requestURI.indexOf(String.valueOf(elementVo))>-1){
                                elementFalg=true;
                            }
                        }
                    }
                    long endTime = System.currentTimeMillis();
                    log.info("拦截器权限拦截所用时间："+(endTime-startTime));
                    if(!elementFalg){
                        log.info(userId+"用户没有权限访问"+requestURI+"数据信息");
                        return false;
                    }
                }

                //重新设置登陆缓存的过期时间
                String tokenKey = Common.USER_LOGIN_HY_TOKEN + userId;
                cacheAsyncService.expire(tokenKey, tokenExpire);
                cacheAsyncService.expire(userIdKey,tokenExpire);

            }
        } else {
            accessFlag = true;
        }

        if(!accessFlag){
            log.info("访问验证失败、需要登录。");

            // 需要登录
            if (clazz.isAnnotationPresent(RestController.class)
                    || method.isAnnotationPresent(ResponseBody.class)){
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                response.getWriter().write(JSONObject.toJSONString(RestVo.SUCCESS(RestMsg.NOT_LOGIN.getName())));
            } else {
                response.sendRedirect(loginPageUri);
            }

            return false;
        }


        return true;
    }
 

    public String getClientIp(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.indexOf(',')!=-1 ){
                ip = ip.split(",")[0];
            }
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")) {
                // 根据网卡取本机配置的IP
                try {
                    ip = InetAddress.getLocalHost().getHostAddress();
                } catch (UnknownHostException e) {
                    log.info("异常" + e.getMessage());
                }
            }
        }
        return ip;
    }
}
