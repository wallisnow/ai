package com.ai.sys.service.sys;

import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.exception.ResourceOperationExceptionFactory;
import com.ai.sys.model.entity.sys.SysMenu;
import com.ai.sys.model.entity.sys.SysRole;
import com.ai.sys.repository.sys.SysMenuRepository;
import com.ai.sys.repository.sys.SysRoleRepository;
import com.ai.sys.utils.ModelUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl implements SysRoleService {

    private final SysRoleRepository sysRoleRepository;
    private final SysMenuRepository sysMenuRepository;

    @Override
    public List<SysRole> findRoleList() {
        return sysRoleRepository.findAll();
    }

    @Override
    public SysRole findSysRoleById(String id) throws ResourceOperationException {
        return sysRoleRepository.findById(id)
                .orElseThrow(() -> ResourceOperationException.builder()
                        .resourceName("role")
                        .message("role not found")
                        .status(HttpStatus.NOT_FOUND).build());
    }

    @Override
    public void create(SysRole role) throws ResourceOperationException {
        String roleName = role.getRoleName();
        Optional<SysRole> roleOptional = sysRoleRepository.findById(roleName);
        if (roleOptional.isPresent()) {
            throw ResourceOperationException.builder()
                    .resourceName("role")
                    .message("role exits already")
                    .status(HttpStatus.FOUND)
                    .build();
        }
        role.setRoleName(roleName.toLowerCase(Locale.ROOT));
        //new role has no menu
        role.setMenus(Set.of());
        sysRoleRepository.save(role);
    }

    @Override
    public void update(SysRole role) throws ResourceOperationException {
        SysRole roleToUpdate = sysRoleRepository.findById(role.getRoleName())
                .orElseThrow(() -> ResourceOperationException.builder()
                        .resourceName("role")
                        .message("role not found")
                        .status(HttpStatus.NOT_FOUND).build());

        Set<SysMenu> menusToUpdate = roleToUpdate.getMenus();
        Set<SysMenu> expectedMenus = role.getMenus();
        if (!ObjectUtils.isEmpty(expectedMenus)) {
            expectedMenus.stream()
                    .map(sysMenu -> {
                        try {
                            List<String> ignores = List.of("serialVersionUID");
                            boolean onlyId = ModelUtils.checkIfOnlyOneFieldHasValue(SysMenu.class, sysMenu, "id", ignores);
                            if (!onlyId) {
                                throw ResourceOperationExceptionFactory.createMenuException("Only menu id could be set!", HttpStatus.NO_CONTENT);
                            }
                        } catch (NoSuchFieldException | IllegalAccessException e) {
                            throw ResourceOperationExceptionFactory.createMenuException("Assign menu failed!", HttpStatus.NO_CONTENT);
                        }
                        return Optional.ofNullable(sysMenu.getId())
                                .orElseThrow(() -> ResourceOperationExceptionFactory.createMenuException("menu id is a mandatory to set", HttpStatus.NO_CONTENT));
                    })
                    .forEach(id -> {
                        SysMenu expectedMenu = sysMenuRepository.getById(id);
                        menusToUpdate.add(expectedMenu);
                    });
        }
        roleToUpdate.setMenus(menusToUpdate);
        sysRoleRepository.save(roleToUpdate);
    }

    @Override
    public void delete(String id) throws ResourceOperationException {
        sysRoleRepository.deleteById(id);
    }
}
