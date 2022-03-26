package com.ai.sys.service.sys;

import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.sys.SysRole;

import java.util.List;

public interface SysRoleService {

    List<SysRole> findRoleList();

    SysRole findSysRoleById(Long id) throws ResourceOperationException;

    void create(SysRole role) throws ResourceOperationException;

    void update(SysRole role) throws ResourceOperationException;

    void delete(Long id) throws ResourceOperationException;
}
