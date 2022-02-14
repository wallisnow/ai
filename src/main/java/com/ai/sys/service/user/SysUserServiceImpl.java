package com.ai.sys.service.user;

import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.user.SysUser;
import com.ai.sys.repository.user.SysUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SysUserServiceImpl implements SysUserService {

    private final SysUserRepository userRepository;

    @Override
    public Optional<SysUser> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public void create(SysUser sysUser) throws ResourceOperationException {
        userRepository.save(sysUser);
    }
}
