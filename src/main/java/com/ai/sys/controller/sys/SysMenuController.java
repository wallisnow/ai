package com.ai.sys.controller.sys;


import com.ai.sys.common.Response;
import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.sys.SysMenu;
import com.ai.sys.service.sys.SysMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/menu")
public class SysMenuController {

    private final SysMenuService sysMenuService;

    @GetMapping("/all")
    public @ResponseBody
    ResponseEntity<List<SysMenu>> findMenu() {
        try {
            List<SysMenu> sysMenuList = sysMenuService.getSysMenuList();
            return ResponseEntity.ok(sysMenuList);
        } catch (ResourceOperationException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    @PostMapping(value = "/add", consumes = {"application/json"})
    public Response addMenu(@RequestBody SysMenu sysMenu) {
        try {
            sysMenuService.create(sysMenu);
            return Response.ok();
        } catch (ResourceOperationException e) {
            log.debug("create menu failed");
            return Response.error(e.getStatus().toString(), e.getMessage());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return Response.error(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e);
        }
    }

    @PutMapping(value = "/modify", consumes = {"application/json"})
    public Response updateMenu(@RequestBody SysMenu sysMenu) {
        try {
            sysMenuService.update(sysMenu);
            return Response.ok("menu updated");
        } catch (ResourceOperationException e) {
            log.debug("update menu failed");
            return Response.error(e.getStatus().toString(), e);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return Response.error(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e);
        }
    }
}
