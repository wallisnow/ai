package com.ai.sys.service.sys;

import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.sys.SysMenu;
import com.ai.sys.repository.sys.SysMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl implements SysMenuService {

    private final SysMenuRepository sysMenuRepository;

    @Override
    public List<SysMenu> getSysMenuList() {
        return sysMenuRepository.findAll();
    }

    @Override
    public void create(SysMenu sysMenu) throws ResourceOperationException {
        sysMenuRepository.save(sysMenu);
    }

    @Override
    public void update(SysMenu sysMenu) throws ResourceOperationException {
        sysMenuRepository.save(sysMenu);
    }

    @Override
    public void delete(Long id) throws ResourceOperationException {
        sysMenuRepository.deleteById(id);
    }
}
