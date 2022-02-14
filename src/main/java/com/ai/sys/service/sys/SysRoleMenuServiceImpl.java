package com.ai.sys.service.sys;

import com.ai.sys.model.entity.sys.SysMenu;
import com.ai.sys.repository.sys.SysMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
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
@RequiredArgsConstructor
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

    private final SysMenuRepository sysMenuRepository;

    @Override
    public Set<String> getPermissionsByRoleIds(Collection<Long> roleIds) {
        List<SysMenu> allMenus = sysMenuRepository.findAllById(roleIds);
        if (!ObjectUtils.isEmpty(allMenus)) {
            return allMenus.stream().map(SysMenu::getPermissions).collect(Collectors.toSet());
        }
        return Set.of();
    }
}
