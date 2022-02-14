package com.ai.sys.service.sys;

import java.util.Collection;
import java.util.Set;

/**
 * <p>
 * 角色菜单关系 服务类
 * </p>
 *
 * @author rstyro
 * @since 2021-07-22
 */
public interface SysRoleMenuService {
    /**
     * 查询所有角色下的所有资源权限
     *
     * @return
     */
    Set<String> getPermissionsByRoleIds(Collection<Long> roleIds);
}
