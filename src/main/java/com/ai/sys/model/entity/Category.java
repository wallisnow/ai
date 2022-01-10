package com.ai.sys.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Entity
@SuperBuilder
@RequiredArgsConstructor
public class Category extends DateAudit {
    @Id
    private String name;
    private String description;
}
