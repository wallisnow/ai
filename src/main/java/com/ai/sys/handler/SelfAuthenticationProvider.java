package com.ai.sys.handler;

import com.ai.sys.security.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 自定义认证逻辑
 */
//@Component
public class SelfAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private SecurityUserService securityUserService;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //获取用户名
        String account = authentication.getName();
        //获取密码
        String password = (String) authentication.getCredentials();
        UserDetails userDetails = securityUserService.loadUserByUsername(account);
        boolean checkPassword = bCryptPasswordEncoder.matches(password, userDetails.getPassword());
        if (!checkPassword) {
            throw new BadCredentialsException("密码不正确，请重新登录!");
        }
        return new UsernamePasswordAuthenticationToken(account, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
