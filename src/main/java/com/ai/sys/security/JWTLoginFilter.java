package com.ai.sys.security;

import com.ai.sys.common.Response;
import com.ai.sys.config.Constant;
import com.ai.sys.model.LoginEntry;
import com.ai.sys.service.user.SysUserService;
import com.ai.sys.utils.JwtUtils;
import com.ai.sys.utils.ServletUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * 自定义登录过滤器
 */
@Slf4j
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    private SysUserService sysUserService;

    /**
     * 父类的构造方法
     *
     * @param defaultFilterProcessesUrl 默认需要过滤的 url
     * @param authenticationManager     权限管理器
     */
    public JWTLoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager, SysUserService sysUserService) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationManager(authenticationManager);
        this.sysUserService = sysUserService;
    }


    /**
     * /**
     * 自定义处理 登录认证，这里使用的json body 登录
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @SneakyThrows
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LoginEntry user = new ObjectMapper().readValue(request.getInputStream(), LoginEntry.class);
//        // 前端提交上来的是明文，数据库保存的密码是简单的 md5 加密，所以这边要和数据库保存的密码算法一致
//        String encryptPwd = MDUtil.bcMD5(user.getPassword());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword());
        return getAuthenticationManager().authenticate(authenticationToken);
    }


    /**
     * 登录成功返回 token
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) {
        SecurityUser principal = (SecurityUser) auth.getPrincipal();
        log.debug("authority=" + principal.getAuthorities().toString());
        String token = JwtUtils.generateKey(new SecurityUser()
                .setUsername(principal.getUsername())
                .setAuthorities(new HashSet<>(principal.getAuthorities())));
        try {
            Map res = new HashMap();
            res.put(Constant.RESP_CODE, 20000);
            res.put(Constant.RESP_MESSAGE, "成功");
            res.put("data", Map.of(
                    Constant.RESP_TOKEN, token,
                    Constant.RESP_MENU, principal.getSysMenus()));

            //登录成功時，返回json格式进行提示
            ServletUtils.render(request,
                    response,
                    Response.put(res));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 失败返回
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        String result = "";
        // 账号过期
        if (failed instanceof AccountExpiredException) {
            result = "账号过期";
        }
        // 密码错误
        else if (failed instanceof BadCredentialsException) {
            result = "密码错误";
        }
        // 密码过期
        else if (failed instanceof CredentialsExpiredException) {
            result = "密码过期";
        }
        // 账号不可用
        else if (failed instanceof DisabledException) {
            result = "账号不可用";
        }
        //账号锁定
        else if (failed instanceof LockedException) {
            result = "账号锁定";
        }
        // 用户不存在
        else if (failed instanceof InternalAuthenticationServiceException) {
            result = "用户不存在";
        }
        // 其他错误
        else {
            result = "未知异常";
        }
        ServletUtils.render(request, response, Response.httpError(result));
    }
}
