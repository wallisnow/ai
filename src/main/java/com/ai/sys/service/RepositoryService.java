package com.ai.sys.service;

import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.Repository;

import java.util.List;

public interface RepositoryService {
    List<Repository> findAll() throws ResourceOperationException;
}
