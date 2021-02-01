package com.github.labazhang.security.security;

import com.github.labazhang.common.utils.R;
import com.github.labazhang.common.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 退出处理器
 * - 删除token
 * - 删除用户登录信息
 *
 * @author laba zhang
 */
@Component
public class TokenLogoutHandler implements LogoutHandler {

    private static final String TOKEN = "token";

    private final TokenManager tokenManager;
    private final RedisTemplate<String, String> redisTemplate;

    public TokenLogoutHandler(TokenManager tokenManager, RedisTemplate<String, String> redisTemplate) {
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 执行退出
     * - header 中删除token
     * - token 不为空，移除Redis中Token
     *
     * @param httpServletRequest  req
     * @param httpServletResponse res
     * @param authentication      auth
     */
    @Override
    public void logout(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       Authentication authentication) {
        String token = httpServletRequest.getHeader(TOKEN);
        if (!StringUtils.isEmpty(token)) {
            tokenManager.removeToken(token);

            String username = tokenManager.getUserInfoFromToken(token);
            redisTemplate.delete(username);
        }
        ResponseUtil.out(httpServletResponse, R.ok());
    }
}
