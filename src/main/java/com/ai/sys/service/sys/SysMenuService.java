package com.ai.sys.service.sys;

import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.sys.SysMenu;

import java.util.List;

public interface SysMenuService {
    List<SysMenu> getSysMenuList();
    void create(SysMenu sysMenu) throws ResourceOperationException;
    void update(SysMenu sysMenu) throws ResourceOperationException;
    void delete(Long id) throws ResourceOperationException;
}
