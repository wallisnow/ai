package com.ai.sys.service.user;

import com.ai.sys.exception.ResourceOperationExceptionFactory;
import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.sys.SysRole;
import com.ai.sys.model.entity.user.SysUser;
import com.ai.sys.repository.user.SysUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
            throw ResourceOperationExceptionFactory.createUserException("user already exists", HttpStatus.CONFLICT);
        }
        userRepository.save(sysUser);
    }

    @Override
    //update by normal user
    public void update(SysUser sysUser) throws ResourceOperationException {
        if (!CollectionUtils.isEmpty(sysUser.getRoles())){
            throw ResourceOperationExceptionFactory.createUserException("role is not allowed to creat, please contact the admin", HttpStatus.FORBIDDEN);
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
        return byId.orElseThrow(() -> ResourceOperationExceptionFactory.createUserException("user not found", HttpStatus.NOT_FOUND));
    }

}
