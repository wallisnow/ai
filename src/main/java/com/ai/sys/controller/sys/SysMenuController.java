package com.ai.sys.controller.sys;


import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.Algo;
import com.ai.sys.model.entity.sys.SysMenu;
import com.ai.sys.service.sys.SysMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Log4j2
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
    public ResponseEntity<String> addMenu(@RequestBody SysMenu sysMenu) {
        try {
            sysMenuService.create(sysMenu);
            return ResponseEntity.ok().build();
        } catch (ResourceOperationException e) {
            log.debug("create menu failed");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
