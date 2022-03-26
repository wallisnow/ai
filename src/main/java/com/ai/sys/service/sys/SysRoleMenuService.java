package com.ai.sys.service.sys;

import java.util.Collection;
import java.util.Set;

public interface SysRoleMenuService {
    Set<String> getPermissionsByRoleNames(Collection<Long> roleNames);
}
