package com.ai.sys.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Model {
    Long id;
    String lastModified;
    String path;
    String name;
    List<Model> children;
}
