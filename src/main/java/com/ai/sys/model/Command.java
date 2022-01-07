package com.ai.sys.model;

import lombok.Value;

import java.util.List;

@Value
public class Command {
    String algoPath;
    List<String> params;
    String dataSetPath;
}
