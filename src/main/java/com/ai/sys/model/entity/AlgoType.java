package com.ai.sys.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@SuperBuilder
@RequiredArgsConstructor
public class AlgoType extends DateAudit {
    @Id
    private String name;
    private String description;
}