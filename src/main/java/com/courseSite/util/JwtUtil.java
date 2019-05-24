package com.courseSite.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JwtUtil {

    /*
    * 设置token过期时间为15分钟
    * */
    private static final long EXPIRE_TIME = 15 * 60 * 60 * 1000;

    /*
    * token私钥
    * */
    private static final String TOKEN_SECRET = UUID.randomUUID().toString().replace("-","");

    /*
    * 检验token是否正确
    * */
    public static boolean verify(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier jwtVerifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = jwtVerifier.verify(token);
            return true;
        } catch (UnsupportedEncodingException e) {
            //e.printStackTrace();
            return false;
        }
    }

    /*
    * 获取登陆的用户ID
    * */
    public static String getUserID(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userID").asString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String sign(Long userID){
        try {
            //过期时间
            Date date = new Date(System.currentTimeMillis()+EXPIRE_TIME);
            //私钥及加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            Map<String,Object> header = new HashMap<>(2);
            header.put("type","JWT");
            header.put("alg","HS256");
            return JWT.create()
                    .withHeader(header)
                    .withClaim("userID",userID)
                    .withExpiresAt(date)
                    .sign(algorithm);

        }catch (UnsupportedEncodingException e){
            //e.printStackTrace();
            return null;
        }
    }
}
