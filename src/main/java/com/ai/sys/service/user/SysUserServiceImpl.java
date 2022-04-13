package com.ai.sys.service.user;

import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.sys.SysMenu;
import com.ai.sys.model.entity.sys.SysRole;
import com.ai.sys.model.entity.user.SysUser;
import com.ai.sys.repository.user.SysUserRepository;
import com.ai.sys.service.sys.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ai.sys.exception.ResourceOperationExceptionFactory.createUserException;

@Service
@RequiredArgsConstructor
public class SysUserServiceImpl implements SysUserService {

    private final SysUserRepository userRepository;

    @Override
    public List<SysUser> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<SysUser> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public void create(SysUser sysUser) throws ResourceOperationException {
        Optional<SysUser> byId = userRepository.findUserByUsername(sysUser.getUsername());
        if (byId.isPresent()) {
            throw createUserException("user already exists", HttpStatus.CONFLICT);
        }
        userRepository.save(sysUser);
    }

    @Override
    //update by normal user
    public void update(SysUser sysUser) throws ResourceOperationException {
        if (!CollectionUtils.isEmpty(sysUser.getRoles())) {
            throw createUserException("role is not allowed to creat, please contact the admin", HttpStatus.FORBIDDEN);
        }
        SysUser user = findSysUser(sysUser);
        Set<SysRole> oldRoles = user.getRoles();
        //cannot set role
        sysUser.setRoles(oldRoles);
        userRepository.save(sysUser);
    }

    @Override
    public void updateWithRole(SysUser sysUser) throws ResourceOperationException {
        findSysUser(sysUser);
        userRepository.save(sysUser);
    }

    private SysUser findSysUser(SysUser sysUser) {
        Optional<SysUser> byId = userRepository.findById(sysUser.getId());
        return byId.orElseThrow(() -> createUserException("user not found", HttpStatus.NOT_FOUND));
    }

}
