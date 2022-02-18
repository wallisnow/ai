package com.ai.sys.security;

import com.ai.sys.common.Response;
import com.ai.sys.config.ConfigConstValue;
import com.ai.sys.utils.JwtUtils;
import com.ai.sys.utils.ServletUtils;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class SecurityAuthTokenFilter extends BasicAuthenticationFilter {

    public static final String DEFAULT_TOKEN_TYPE = "Bearer";

    public SecurityAuthTokenFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(request);
        if (StringUtils.hasLength(token)) {
            SecurityUser userInfo = JwtUtils.getUserInfoByToken(token);
            if (userInfo == null) {
                ServletUtils.render(request, response, Response.httpError("Token is expired or invalid"));
                return;
            }
            if (StringUtils.hasLength(userInfo.getUsername()) && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 如果没过期，保持登录状态
                if (!JwtUtils.isExpiration(token)) {
                    // 将用户信息存入 authentication，方便后续校验
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userInfo.getUsername(), null, userInfo.getAuthorities());
                    // SecurityContextHolder 权限验证上下文
                    SecurityContext context = SecurityContextHolder.getContext();
                    // 指示用户已通过身份验证
                    context.setAuthentication(authentication);
                    log.debug("authority=" + authentication.getAuthorities().toString());
                }
            }
        }
        // 继续下一个过滤器
        filterChain.doFilter(request, response);
    }

    public String getToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(ConfigConstValue.TOKEN);
        if (!StringUtils.hasLength(bearerToken)) {
            bearerToken = request.getParameter(ConfigConstValue.TOKEN);
        }
        return bearerToken;
    }
}
