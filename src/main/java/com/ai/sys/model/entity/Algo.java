package com.ai.sys.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
public class Algo extends DateAudit{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NonNull
    @Column(unique = true)
    private String name;
    @NonNull
    private String path;
    private String description;

    @ManyToOne(cascade={CascadeType.MERGE})
    @JoinColumn(name = "algotype_name")
    private AlgoType algoType;
}