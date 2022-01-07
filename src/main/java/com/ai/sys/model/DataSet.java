package com.ai.sys.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
public class DataSet extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NonNull
    @Column(unique = true)
    private String name;
    @NonNull
    private String path;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Tag> tags;
}
