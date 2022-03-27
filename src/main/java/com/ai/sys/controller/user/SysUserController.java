package com.ai.sys.controller.user;


import com.ai.sys.common.Response;
import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.sys.SysRole;
import com.ai.sys.model.entity.user.SysUser;
import com.ai.sys.service.sys.SysRoleService;
import com.ai.sys.service.user.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static com.ai.sys.config.Constant.ROLE_USER;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class SysUserController {

    private final SysUserService sysUserService;
    private final SysRoleService sysRoleService;
    /**
     * spring security 加密方式
     */
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/all")
    public @ResponseBody
    Response listAllUsers() {
        try {
            List<SysUser> all = sysUserService.findAll();
            return Response.httpOk(all);
        } catch (ResourceOperationException e) {
            return Response.httpError(e.getStatus(), e.getMessage());
        }
    }

    @PostMapping(value = "/new", consumes = {"application/json"})
    @ResponseBody
    public Response register(@RequestBody SysUser user) {
        SysRole userRole = sysRoleService.findSysRoleByRoleName(ROLE_USER);
        SysUser newUser = SysUser.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .roles(Set.of(userRole))
                .build();
        try {
            sysUserService.create(newUser);
        } catch (ResourceOperationException e) {
            return Response.httpError(e.getStatus(), e.getMessage());
        }
        return Response.httpOk("User registration successful");
    }

    @PutMapping(value = "/modify", consumes = {"application/json"})
    public Response updateUser(@RequestBody SysUser user) {
        try {
            sysUserService.update(user);
            return Response.httpOk("Update user successful");
        } catch (ResourceOperationException e) {
            log.debug("create category failed");
            return Response.httpError(e.getStatus(), e.getMessage());
        }
    }
}
