package com.ai.sys.repository.sys;

import com.ai.sys.model.entity.sys.SysMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysMenuRepository extends JpaRepository<SysMenu, Long> {
}
