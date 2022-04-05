package com.ai.sys.service;

import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.Algo;
import com.ai.sys.model.entity.AlgoResult;

import java.util.List;

public interface AlgoResultService {
    void create(AlgoResult algoResult);
}
