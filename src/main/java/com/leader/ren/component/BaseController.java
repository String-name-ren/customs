package com.leader.ren.component;


import com.leader.ren.common.constant.RestMsg;
import com.leader.ren.common.exception.BusinessException;
import com.leader.ren.common.jwt.IJWTInfo;
import com.leader.ren.common.jwt.JWTHelper;
import com.leader.ren.common.jwt.JWTInfo;
import com.leader.ren.common.utils.BaseContext;
import com.leader.ren.component.config.CacheAsyncService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class BaseController {
    // 令牌头
    @Value("${jwt.user.token-header:}")
    private String tokenHeader;
    // 公钥
    @Value("${sys.security.publicKey:}")
    private String publicKey;

    @Autowired(required = false)
    protected HttpServletRequest request;

    @Autowired(required = false)
    protected HttpServletResponse response;

    @Autowired(required = false)
    CacheAsyncService cacheAsyncService;

    public String getClientIp(){
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
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

    public Long getUserId() {
        try {
            String token = getToken();
            IJWTInfo jwtInfo = JWTHelper.parserToken(token, publicKey);
            Long userId = Long.parseLong(jwtInfo.getId());
            BaseContext.set("userId", userId);
            return userId;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(RestMsg.NOT_LOGIN);
        }
    }

    /**
     * 获取用户登录信息
     *
     * @return
     */
    public IJWTInfo getUser() {
        try {
            IJWTInfo ijwtInfo = JWTHelper.parserToken(getToken(), publicKey);
            List<Long> list=new ArrayList<Long>();
            //转换数据类型
            /*if(null!=ijwtInfo.getDeptUnitId()){
                for(Number l:ijwtInfo.getDeptUnitId()){
                    list.add(Long.valueOf(String.valueOf(l)));
                }
            }*/
            return new JWTInfo(ijwtInfo.getId(),ijwtInfo.getCode(),ijwtInfo.getName(),ijwtInfo.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(RestMsg.NOT_LOGIN);
        }

    }

}