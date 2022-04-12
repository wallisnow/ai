package com.ai.sys.service;

import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.Model;

import java.io.IOException;
import java.util.List;


public interface ModelService {
    List<Model> findAll();
    boolean deleteByPath(String path) throws ResourceOperationException, IOException;
}
