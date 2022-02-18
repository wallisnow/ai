package com.ai.sys.model.entity.sys;

import com.ai.sys.model.entity.DateAudit;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
public class SysMenu extends DateAudit {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 菜单URL
     */
    private String url;

    /**
     * 菜单名称
     */
    private String menuValue;

    /**
     * 授权(多个用逗号分隔，如：sys:user:list,sys:user:save)
     */
    private String permissions;

    /**
     * 类型   0：菜单   1：按钮   2：页面
     */
    private Integer type;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建者
     */
    private Long createBy;

    /**
     * 更新者
     */
    private Long updateBy;

    /**
     * 0:未删除    1:已删除
     */
    private Boolean deleted;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    @JsonBackReference
    private SysMenu parent;

    @OneToMany(mappedBy="parent")
    private List<SysMenu> submenus;
}
