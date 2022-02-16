package com.ai.sys.service.user;

import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.user.SysUser;

import java.util.Optional;

public interface SysUserService {
    Optional<SysUser> findUserByUsername(String username);
    void create(SysUser sysUser) throws ResourceOperationException;
    void update(SysUser sysUser) throws ResourceOperationException;
    void updateWithRole(SysUser sysUser) throws ResourceOperationException;
}
