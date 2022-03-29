package com.ai.sys.service;

import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.Category;

import java.util.List;

public interface CategoryService {
    void create(Category category) throws ResourceOperationException;
    void delete(Long id) throws ResourceOperationException;
    Category find(Long id) throws ResourceOperationException;
    List<Category> findAll() throws ResourceOperationException;
}
