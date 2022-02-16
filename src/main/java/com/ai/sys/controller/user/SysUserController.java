package com.ai.sys.controller.user;


import com.ai.sys.common.Response;
import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.sys.SysRole;
import com.ai.sys.model.entity.user.SysUser;
import com.ai.sys.service.user.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.ai.sys.config.Constant.ROLE_USER;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class SysUserController {

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
                .roles(Set.of(SysRole.builder().roleName(ROLE_USER).roleDesc(ROLE_USER).build()))
                .build();
        try {
            sysUserService.create(newUser);
        } catch (ResourceOperationException e) {
            return Response.error(e.getStatus().toString(), e);
        }
        return Response.ok("注册成功");
    }

    @PostMapping(value = "/update", consumes = {"application/json"})
    public Response updateUser(@RequestBody SysUser user) {
        try {
            sysUserService.update(user);
            return Response.ok("修改成功");
        } catch (ResourceOperationException e) {
            log.debug("create category failed");
            return Response.error(e.getStatus().toString(), e);
        }
    }
}
