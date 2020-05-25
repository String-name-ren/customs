package com.leader.ren.common.jwt;


import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class JWTHelper {
    public static final String JWT_KEY_CODE = "code";
    public static final String JWT_KEY_NAME = "name";
    public static final String JWT_KEY_TIME = "time";
    public static final String JWT_KEY_ADMIN = "admin";
    public static final String JWT_KEY_SHOPID = "shopId";
    public static final String JWT_KEY_DEPTUNITID = "deptUnitId";
    /**
     * 私钥加密token
     *
     * @param jwtInfo
     * @param privateKey
     * @return String token
     * @throws Exception
     */
    public static String encodeToken(IJWTInfo jwtInfo, String privateKey) throws Exception {
        Key key = RSA.buildPrivateKey(privateKey);

        String compactJws = Jwts.builder()
                .setSubject(jwtInfo.getId())
                .claim(JWT_KEY_CODE, jwtInfo.getId())
                .claim(JWT_KEY_NAME, jwtInfo.getName())
                .claim(JWT_KEY_TIME, jwtInfo.getTime())
                .signWith(SignatureAlgorithm.RS256, key)
                .compact();

        return compactJws;
    }

    /**
     * 私钥加密token
     *
     * @param jwtInfo
     * @param privateKey
     * @param expireDate
     * @return String token
     * @throws Exception
     */
    public static String encodeToken(IJWTInfo jwtInfo, String privateKey, Date expireDate) throws Exception {
        Key key = RSA.buildPrivateKey(privateKey);

        String compactJws = Jwts.builder()
                .setSubject(jwtInfo.getId())
                .claim(JWT_KEY_CODE, jwtInfo.getId())
                .claim(JWT_KEY_NAME, jwtInfo.getCode())
                .claim(JWT_KEY_TIME, jwtInfo.getTime())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.RS256, key)
                .compact();

        return compactJws;
    }

    /**
     * 公钥解析token
     *
     * @param token
     * @param publicKey
     *
     * @return
     * @throws Exception
     */
    public static Jws<Claims> decodeToken(String token, String publicKey) throws Exception {
        Key key = RSA.buildPublicKey(publicKey);

        JwtParser parser = Jwts.parser();
        JwtParser jwtParser = Jwts.parser().setSigningKey(key);

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);

        return claimsJws;
    }

    /**
     * 获取token中的用户信息
     *
     * @param token
     * @param publicKey
     * @return IJWTInfo
     * @throws Exception
     */
    public static IJWTInfo parserToken(String token, String publicKey) throws Exception {
        log.info("token:"+token);
        log.info("publicKey:"+publicKey);
        Jws<Claims> claimsJws = decodeToken(token, publicKey);
        log.info("claimsJws:"+claimsJws);
        Claims body = claimsJws.getBody();
        log.info("body:"+body.getId()+body.getSubject()+body);
        return new JWTInfo(body.getSubject(),
                chgObj2Str(body.get(JWT_KEY_CODE)),
                chgObj2Str(body.get(JWT_KEY_NAME)),
                chgObj2Log(body.get(JWT_KEY_TIME))
        );
    }




    public static String chgObj2Str(Object obj){
        return obj==null?"":obj.toString();
    }

    public static Long chgObj2Log(Object obj){
        return obj==null? 0L: Long.parseLong(obj.toString());
    }

    public static Integer chgObj2Int(Object obj){
        return obj==null? 0: Integer.parseInt(obj.toString());
    }

    public static List<Long> chgObj2ListLog(Object obj){

        if(obj==null){
            return null;
        }
        List<Long> list=new ArrayList<Long>();
        list=(List<Long>) obj;
        return list;
//        return obj==null? null: Long.parseLong(obj.toString());
    }
}