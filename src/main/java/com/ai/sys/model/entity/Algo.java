package com.ai.sys.model.entity;

import com.ai.sys.model.entity.user.SysUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
public class Algo extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NonNull
    @Column(unique = true)
    private String name;
    private String path;
    private String description;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "userid")
    private SysUser sysUser;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "dataset_id")
    private DataSet dataSet;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "repository_id")
    private Repository repository;

    private Boolean isCompleted;

    @OneToMany
    @JoinColumn(name = "algoid")
    private List<AlgoResult> algoResults;
}
