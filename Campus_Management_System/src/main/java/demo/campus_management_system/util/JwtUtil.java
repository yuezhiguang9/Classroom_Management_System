package demo.campus_management_system.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    //安全密钥
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    //jwt有效期
    private static final long EXPIRATION_TIME = 60 * 60 * 24;
    
    //提取纯Token（去掉Bearer前缀）
    public static String extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return authHeader;
    }

    //生成jwt token
    public static String createToken(String user_account, String user_password) {
        return Jwts.builder()
                .setSubject(user_account)   //标准声明
                .claim("user_password", user_password)    //自定义声明
                .setIssuedAt(new Date())    //签发时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME * 1000))    //过期时间
                .signWith(SECRET_KEY)   //签名算法和密钥
                .compact();
    }


    //解析前端传来的token
    public static Jws<Claims> parseToken(String token) {

        try {
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
        } catch (JwtException e) {
            throw new RuntimeException("非法的JWTtoken", e);
        }
    }

    //获取用户信息

    //获取账号
    public static String getUserAccountToken(String token) {
        try {
            return parseToken(token).getBody().getSubject();
        } catch (RuntimeException e) {
            return "error";
        }

    }

    //获取密码
    public static String getUserPasswordToken(String token) {
        try {
            return parseToken(token).getBody().get("user_password", String.class);
        } catch (RuntimeException e) {
            return "error";
        }
    }

    // 在 JwtUtil.java 中添加
    public static String getUserTypeFromToken(String token) {
        try {
            // 假设我们在创建 token 时添加了用户类型信息
            return parseToken(token).getBody().get("user_type", String.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse user type from token", e);
        }
    }



}
