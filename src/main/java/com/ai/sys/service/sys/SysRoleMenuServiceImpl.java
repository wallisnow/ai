package com.ai.sys.service.sys;

import com.ai.sys.model.entity.sys.SysMenu;
import com.ai.sys.model.entity.sys.SysRole;
import com.ai.sys.repository.sys.SysRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

    private final SysRoleRepository sysRoleRepository;

    @Override
    public Set<String> getPermissionsByRoleNames(Collection<String> roleNames) {
        List<SysRole> roles = sysRoleRepository.findAllById(roleNames);
        Set<String> permissions = new HashSet<>();
        if (!ObjectUtils.isEmpty(roles)) {
            roles.forEach(sysRole -> {
                Set<SysMenu> menus = sysRole.getMenus();
                if (!ObjectUtils.isEmpty(menus)){
                    menus.forEach(sysMenu -> {
                        permissions.add(sysMenu.getPermissions());
                    });
                }
            });

        }
        return permissions;
    }
}
