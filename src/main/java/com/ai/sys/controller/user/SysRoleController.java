package com.ai.sys.controller.user;


import com.ai.sys.common.Response;
import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.sys.SysMenu;
import com.ai.sys.model.entity.sys.SysRole;
import com.ai.sys.model.entity.user.SysUser;
import com.ai.sys.service.sys.SysRoleService;
import com.ai.sys.service.user.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/role")
public class SysRoleController {

    private final SysUserService sysUserService;
    private final SysRoleService sysRoleService;

    @GetMapping("/all")
    public @ResponseBody
    Response listAllRoles() {
        try {
            List<SysRole> roleList = sysRoleService.findRoleList();
            return Response.httpOk(roleList);
        } catch (ResourceOperationException e) {
            return Response.httpError(e.getStatus(), e.getMessage());
        }
    }

    @PostMapping(value = "/add", consumes = {"application/json"})
    public Response addSysRole(@RequestBody SysRole sysRole) {
        try {
            sysRoleService.create(sysRole);
            return Response.httpOk("Role Created");
        } catch (ResourceOperationException e) {
            log.debug("create Role failed");
            return Response.httpError(e.getStatus(), e.getMessage());
        }
    }

    @PutMapping(value = "/modify", consumes = {"application/json"})
    public Response updateSysRole(@RequestBody SysRole sysRole) {
        try {
            sysRoleService.update(sysRole);
            return Response.httpOk("Role updated");
        } catch (ResourceOperationException e) {
            log.debug("update Role failed");
            return Response.httpError(e.getStatus(), e.getMessage());
        }
    }

    @PutMapping(value = "/user/update", consumes = {"application/json"})
    public Response updateUser(@RequestBody SysUser user) {
        try {
            sysUserService.updateWithRole(user);
            return Response.httpOk("update successful");
        } catch (ResourceOperationException e) {
            log.debug("update user failed");
            return Response.httpError(e.getStatus(), e.getMessage());
        }
    }

    @DeleteMapping("/delete/{role}")
    public Response deleteSysRole(@PathVariable("role") String role) {
        try {
            sysRoleService.delete(role);
            return Response.httpOk("Deleted");
        } catch (ResourceOperationException e) {
            log.debug(e.getStackTrace());
            return Response.httpError(e.getStatus(), e.getMessage());
        }
    }
}
