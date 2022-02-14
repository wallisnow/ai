package com.ai.sys.handler;

import com.ai.sys.common.Response;
import com.ai.sys.utils.ServletUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理匿名用户无权访问
 */
@Component
public class AnonymousAuthenticationEntryPoint implements AuthenticationEntryPoint {

    //未登录时返回给前端数据
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        ServletUtils.render(request,response, Response.error("需要登录"));
    }
}
