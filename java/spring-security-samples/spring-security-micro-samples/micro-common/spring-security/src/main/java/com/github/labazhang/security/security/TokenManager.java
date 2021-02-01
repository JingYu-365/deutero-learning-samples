package com.github.labazhang.security.security;

import io.jsonwebtoken.CompressionCodec;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Token 处理工具
 *
 * @author laba zhang
 */
@Component
public class TokenManager {

    /**
     * token 默认时长
     */
    private static final long TOKEN_EXPIRE = 24 * 60 * 60 * 1000;

    /**
     * 默认签名密钥
     */
    private static final String TOKEN_SIGN_KEY = "123456";

    /**
     * 根据用户名生成token
     *
     * @param username 用户名
     * @return token
     */
    public String createToken(String username) {
        return Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRE))
                .signWith(SignatureAlgorithm.ES512, TOKEN_SIGN_KEY).compressWith(CompressionCodecs.GZIP)
                .compact();
    }

    /**
     * token 中解析出用户名
     *
     * @param token token
     * @return username
     */
    public String getUserInfoFromToken(String token) {
        return Jwts.parser().setSigningKey(TOKEN_SIGN_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * 删除token
     */
    public void removeToken(String token) {

    }
}
