package com.rgfxyjz.test.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    private static final String SECRET_KEY = "meme"; // 签名密钥
    private static final long EXPIRATION_TIME = 24 * 3600 * 1000; // 有效期24小时
    private static final long EXPIRATION_TIME_LONG = 7 * 24 * 3600 * 1000; // 有效期24小时

    // 生成JWT令牌
    public static String genJwt(String username, Integer id,Integer role,Boolean RememberMe) {
        Long Time = EXPIRATION_TIME;
        if(RememberMe) {
            Time = EXPIRATION_TIME_LONG;
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("username", username);
        claims.put("role",role);
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 签名算法
                .setExpiration(new Date(System.currentTimeMillis() + Time)) // 有效期
                .compact();
    }

    // 解析JWT令牌
    public static Claims parseJwt(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY) // 指定签名密钥（必须保证和生成令牌时使用相同的签名密钥）
                .parseClaimsJws(token)
                .getBody();
    }

    // 检查JWT令牌是否过期
    public static boolean isTokenExpired(String token) {
        Claims claims = parseJwt(token);
        return claims.getExpiration().before(new Date());
    }

    // 从JWT令牌中获取用户名
    public static String getUsernameFromToken(String token) {
        Claims claims = parseJwt(token);
        return claims.get("username", String.class);
    }

    // 从JWT令牌中获取用户ID
    public static Integer getIdFromToken(String token) {
        Claims claims = parseJwt(token);
        return claims.get("id", Integer.class);
    }

    // 从JWT令牌中获取角色类型
    public static Integer getRoleFromToken(String token) {
        Claims claims = parseJwt(token);
        return claims.get("role", Integer.class);
    }


    // 获取用户姓名
    public static String getFullNameFromToken(String token) {
        Claims claims = parseJwt(token);
        return claims.get("fullname", String.class);
    }

    // 获取用户学号
    public static String getStudentIdFromToken(String token) {
        Claims claims = parseJwt(token);
        return claims.get("student_id", String.class);
    }
}
