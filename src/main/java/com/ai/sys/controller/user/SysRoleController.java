package com.ai.sys.controller.user;


import com.ai.sys.common.Response;
import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.sys.SysRole;
import com.ai.sys.model.entity.user.SysUser;
import com.ai.sys.service.sys.SysRoleService;
import com.ai.sys.service.user.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
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
            return Response.ok(roleList);
        } catch (ResourceOperationException e) {
            return Response.error(e.getStatus().toString(), e);
        }
    }

    @PostMapping(value = "/add", consumes = {"application/json"})
    public Response addSysRole(@RequestBody SysRole sysRole) {
        try {
            sysRoleService.create(sysRole);
            return Response.ok("Role Created");
        } catch (ResourceOperationException e) {
            log.debug("create Role failed");
            return Response.error(e.getStatus().toString(), e);
        }
    }

    @PutMapping(value = "/modify", consumes = {"application/json"})
    public Response updateSysRole(@RequestBody SysRole sysRole) {
        try {
            sysRoleService.update(sysRole);
            return Response.ok("Role updated");
        } catch (ResourceOperationException e) {
            log.debug("update Role failed");
            return Response.error(e.getStatus().toString(), e);
        }
    }

    @PutMapping(value = "/user/update", consumes = {"application/json"})
    public Response updateUser(@RequestBody SysUser user) {
        try {
            sysUserService.updateWithRole(user);
            return Response.ok("update successful");
        } catch (ResourceOperationException e) {
            log.debug("update user failed");
            return Response.error(e.getStatus().toString(), e);
        }
    }

    @DeleteMapping("/delete/{role}")
    public Response deleteSysRole(@PathVariable("role") String role) {
        try {
            sysRoleService.delete(role);
            return Response.ok("Deleted");
        } catch (ResourceOperationException e) {
            log.debug(e.getStackTrace());
            return Response.error(e.getStatus().toString(), e);
        }
    }
}
