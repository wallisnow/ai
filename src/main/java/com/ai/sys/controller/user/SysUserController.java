package com.ai.sys.controller.user;


import com.ai.sys.common.Response;
import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.sys.SysRole;
import com.ai.sys.model.entity.user.SysUser;
import com.ai.sys.service.user.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class SysUserController {

    public static final String USER_ROLE_STR = "USER";

    private final SysUserService sysUserService;
    /**
     * spring security 加密方式
     */
    private final PasswordEncoder passwordEncoder;

    @PostMapping(value = "/new", consumes = {"application/json"})
    @ResponseBody
    public Response register(@RequestBody SysUser user) {
        SysUser newUser = SysUser.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .roles(Set.of(SysRole.builder().roleName(USER_ROLE_STR).roleDesc(USER_ROLE_STR).build()))
                .build();
        try {
            sysUserService.create(newUser);
        } catch (ResourceOperationException e) {
            e.printStackTrace();
            return Response.error("注册失败");
        }
        return Response.ok("注册成功");
    }
}
