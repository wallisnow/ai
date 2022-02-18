package com.ai.sys.security;

import com.ai.sys.config.Constant;
import com.ai.sys.model.entity.sys.SysRole;
import com.ai.sys.model.entity.user.SysUser;
import com.ai.sys.service.sys.SysRoleMenuService;
import com.ai.sys.service.user.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SecurityUserService implements UserDetailsService {

    /**
     * 自己系统的用户体系
     */
    private SysUserService userService;
    private SysRoleMenuService sysRoleMenuService;
    /**
     * spring security 加密方式
     */
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserService(SysUserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setSysRoleMenuService(SysRoleMenuService sysRoleMenuService) {
        this.sysRoleMenuService = sysRoleMenuService;
    }

    @Lazy
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SysUser> userOptional = userService.findUserByUsername(username);
        SysUser user = userOptional.orElseThrow(() -> new UsernameNotFoundException("用户名或密码错误！！"));

        //获取用户权限，并把其添加到GrantedAuthority中
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        Set<SysRole> roles = user.getRoles();

        if (!ObjectUtils.isEmpty(roles)) {
            Set<String> roleNames = roles.stream().map(SysRole::getRoleName).collect(Collectors.toSet());
            Set<String> rolePermissions = sysRoleMenuService.getPermissionsByRoleNames(roleNames);

            // 添加 角色权限
            roles.forEach(r -> {
                // spring security 角色权限默认前缀有：ROLE_
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + r.getRoleName().trim());
                grantedAuthorities.add(grantedAuthority);
            });

            //添加资源权限
            rolePermissions.forEach(permission -> {
                // GrantedAuthority grantedAuthority=new SimpleGrantedAuthority("ROLE_"+permission.trim());
                // 这里不加前缀，可以使用 hasAuthority() 方法
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.trim());
                grantedAuthorities.add(grantedAuthority);
            });
        }

        //Remove this !!!
        if (username.equals("admin")) {
            SecurityUser securityUser = new SecurityUser();
            securityUser.setUsername(user.getUsername())
                    .setPassword(user.getPassword())
                    .setAuthorities(Set.of(new SimpleGrantedAuthority("ROLE_" + Constant.ROLE_SUPER_ADMIN)));
            return securityUser;
        }

        SecurityUser securityUser = new SecurityUser();
        securityUser.setUsername(user.getUsername())
                .setPassword(user.getPassword())
                .setAuthorities(grantedAuthorities);
        return securityUser;
    }
}
