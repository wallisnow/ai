package com.ai.sys.service.sys;

import java.util.Collection;
import java.util.Set;

public interface SysRoleMenuService {
    /**
     * 查询所有角色下的所有资源权限
     *
     * @return
     */
    Set<String> getPermissionsByRoleIds(Collection<Long> roleIds);
}
