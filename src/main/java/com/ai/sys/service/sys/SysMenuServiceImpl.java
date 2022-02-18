package com.ai.sys.service.sys;

import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.exception.ResourceOperationExceptionFactory;
import com.ai.sys.model.entity.sys.SysMenu;
import com.ai.sys.repository.sys.SysMenuRepository;
import com.ai.sys.utils.ModelUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl implements SysMenuService {

    private final SysMenuRepository sysMenuRepository;

    @Override
    public List<SysMenu> getSysMenuList() {
        return sysMenuRepository.findAll();
    }

    @Override
    public void create(SysMenu sysMenu) throws ResourceOperationException, NoSuchFieldException, IllegalAccessException {
        checkSubMenus(sysMenu);
        updateParentMenu(sysMenu);
        sysMenu.setSubmenus(List.of());
        sysMenuRepository.save(sysMenu);
    }

    private void updateParentMenu(SysMenu sysMenu) throws NoSuchFieldException, IllegalAccessException {
        SysMenu parent = sysMenu.getParent();
        if (ObjectUtils.isEmpty(parent)) {
            return;
        }
        boolean onlyId = ModelUtils.checkIfOnlyOneFieldHasValue(SysMenu.class, parent, ModelUtils.include_id, List.of(ModelUtils.ignore_serialVersionUID));
        if (!onlyId) {
            throw ResourceOperationExceptionFactory.createMenuException("Only parent menu id could be set!", HttpStatus.NO_CONTENT);
        }
        if (!ObjectUtils.isEmpty(parent)) {
            Long expectedParentMenuId = Optional.ofNullable(parent.getId())
                    .orElseThrow(() -> ResourceOperationExceptionFactory
                            .createMenuException("Parent menu id is empty!", HttpStatus.NOT_FOUND));
            SysMenu retrievedParentMenu = sysMenuRepository.findById(expectedParentMenuId)
                    .orElseThrow(() -> ResourceOperationExceptionFactory
                            .createMenuException("Parent menu cannot find by provided ID", HttpStatus.NOT_FOUND));
            sysMenu.setParent(retrievedParentMenu);
        }
    }

    @Override
    public void update(SysMenu sysMenu) throws ResourceOperationException, NoSuchFieldException, IllegalAccessException {
        checkSubMenus(sysMenu);
        Long id = sysMenu.getId();
        if (ObjectUtils.isEmpty(id)) {
            throw ResourceOperationExceptionFactory.createMenuException("Menu id is empty!", HttpStatus.NOT_FOUND);
        }
        sysMenuRepository.findById(id)
                .orElseThrow(() -> ResourceOperationExceptionFactory.createMenuException("Menu cannot find by provided ID", HttpStatus.NOT_FOUND));
        updateParentMenu(sysMenu);
        sysMenuRepository.save(sysMenu);
    }

    private void checkSubMenus(SysMenu sysMenu) {
        if (!ObjectUtils.isEmpty(sysMenu.getSubmenus())) {
            log.debug("sub menu is not empty.");
            throw ResourceOperationExceptionFactory.createMenuException("Sub menu could be not be set when create a menu", HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public void delete(Long id) throws ResourceOperationException {
        sysMenuRepository.deleteById(id);
    }
}
