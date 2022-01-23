package com.ai.sys.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Command {
    String algoPath;
    List<String> params;
    String dataSetPath;
}
