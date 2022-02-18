package com.ai.sys.service.sys;

import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.sys.SysRole;
import com.ai.sys.repository.sys.SysRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl implements SysRoleService {

    private final SysRoleRepository sysRoleRepository;

    @Override
    public List<SysRole> findRoleList() {
        return sysRoleRepository.findAll();
    }

    @Override
    public SysRole findSysRoleById(String id) throws ResourceOperationException {
        Optional<SysRole> roleOptional = sysRoleRepository.findById(id);
        return roleOptional.orElseThrow(() -> ResourceOperationException.builder()
                .resourceName("role")
                .message("role not found")
                .status(HttpStatus.NOT_FOUND).build());
    }

    @Override
    public void create(SysRole role) throws ResourceOperationException {
        Optional<SysRole> roleOptional = sysRoleRepository.findById(role.getRoleName());
        if (roleOptional.isPresent()){
            throw ResourceOperationException.builder()
                    .resourceName("role")
                    .message("role exits already")
                    .status(HttpStatus.FOUND)
                    .build();
        }
        //new role has no menu
        role.setMenus(Set.of());
        sysRoleRepository.save(role);
    }

    @Override
    public void update(SysRole role) throws ResourceOperationException {
        Optional<SysRole> roleOptional = sysRoleRepository.findById(role.getRoleName());
        roleOptional.orElseThrow(() -> ResourceOperationException.builder()
                .resourceName("role")
                .message("role not found")
                .status(HttpStatus.NOT_FOUND).build());
        sysRoleRepository.save(role);
    }

    @Override
    public void delete(String id) throws ResourceOperationException {
        sysRoleRepository.deleteById(id);
    }
}
