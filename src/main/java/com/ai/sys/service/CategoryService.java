package com.ai.sys.service;

import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.Category;

public interface CategoryService {
    void create(Category category) throws ResourceOperationException;
    void delete(String name) throws ResourceOperationException;
    Category find(String name) throws ResourceOperationException;
}
