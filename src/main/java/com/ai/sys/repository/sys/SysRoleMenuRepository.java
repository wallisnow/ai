package com.ai.sys.repository.sys;

import com.ai.sys.model.entity.sys.SysRoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 角色菜单关系 服务类
 * </p>
 *
 * @author rstyro
 * @since 2021-07-22
 */
@Repository
public interface SysRoleMenuRepository extends JpaRepository<SysRoleMenu, Long> {
}
