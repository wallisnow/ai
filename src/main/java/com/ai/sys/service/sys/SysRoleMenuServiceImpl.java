package com.ai.sys.service.sys;

import com.ai.sys.model.entity.sys.SysMenu;
import com.ai.sys.model.entity.sys.SysRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色菜单关系 服务实现类
 * </p>
 *
 * @author rstyro
 * @since 2021-07-22
 */
@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

    private SysMenuService sysMenuService;

    @Autowired
    public void setSysMenuService(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }

    @Override
    public Set<String> getPermissionsByRoleIds(Collection<Long> roleIds) {
        List<SysRoleMenu> list = this.list(new LambdaQueryWrapper<SysRoleMenu>().in(SysRoleMenu::getRoleId, roleIds));
        if (!ObjectUtils.isEmpty(list)) {
            Set<Long> menuIds = list.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toSet());
            List<SysMenu> sysMenus = sysMenuService.listByIds(menuIds);
            // 所有菜单资源权限
            return sysMenus.stream().map(SysMenu::getPermissions).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }
}
