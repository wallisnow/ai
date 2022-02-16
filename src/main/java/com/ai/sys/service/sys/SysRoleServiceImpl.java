package com.ai.sys.service.sys;

import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.sys.SysRole;
import com.ai.sys.repository.sys.SysRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl implements SysRoleService {

    private final SysRoleRepository sysRoleRepository;

    @Override
    public List<SysRole> findRoleList() {
        return sysRoleRepository.findAll();
    }

    @Override
    public SysRole findSysRoleById(Long id) throws ResourceOperationException {
        Optional<SysRole> roleOptional = sysRoleRepository.findById(id);
        return roleOptional.orElseThrow(() -> ResourceOperationException.builder()
                .resourceName("role")
                .message("role not found")
                .status(HttpStatus.NOT_FOUND).build());
    }

    @Override
    public void create(SysRole role) throws ResourceOperationException {
        sysRoleRepository.save(role);
    }

    @Override
    public void update(SysRole role) throws ResourceOperationException {
        sysRoleRepository.save(role);
    }

    @Override
    public void delete(Long id) throws ResourceOperationException {
        sysRoleRepository.deleteById(id);
    }
}
