package com.ai.sys.service;

import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.AlgoType;

public interface AlgoTypeService {
    void create(AlgoType algoType) throws ResourceOperationException;

    void delete(String name) throws ResourceOperationException;

    AlgoType find(String name) throws ResourceOperationException;
}
