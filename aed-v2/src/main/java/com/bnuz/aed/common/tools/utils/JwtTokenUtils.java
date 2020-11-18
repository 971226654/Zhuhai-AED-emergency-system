package com.bnuz.aed.common.tools.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

/**
 * @author Leia Liang
 */
public class JwtTokenUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtils.class);

    /** token过期时间 */
    private static final long EXPIRE = 1000 * 60 * 60 * 24;
    /** 密钥 */
    private static final String SECRET_KEY = "ZhuHai-aed";

    /**
     * 生成Token
     * @param userId
     * @param role
     * @return
     */
    public static String generateToken(String userId, String role){
        SecretKey key = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date exp = new Date(nowMillis + EXPIRE);
        System.out.println("token-time: [now]" + now + "\n[exp]" + exp);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject("user")
                .setIssuedAt(now)
                .setExpiration(exp)
                .claim("userId", userId)
                .claim("role", role)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    /** 生成KEY */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.decodeBase64(SECRET_KEY);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /** 从token中获取JWT中的负载 */
    public static Claims getClaimsFromToken(String token) {
        SecretKey secretKey = generalKey();
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            LOGGER.info("JWT格式验证失败: {}", token);
        }
        return claims;
    }

    /**
     * 判断token是否存在与有效
     * @param token
     * @return
     */
    public static boolean checkToken(String token) {
        SecretKey secretKey = generalKey();
        if(StringUtils.isEmpty(token)) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * 判断token是否过期
     * @param token
     * @return
     */
    public static boolean isTokenExpired(String token) {
        Claims claims = getClaimsFromToken(token);
        Date expiredDate = claims.getExpiration();
        return expiredDate.before(new Date());
    }

}
