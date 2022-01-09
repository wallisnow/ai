package com.ai.sys.service;

import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.Category;
import com.ai.sys.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public void create(Category category) throws ResourceOperationException {
        categoryRepository.save(category);
    }

    @Override
    public void delete(String name) throws ResourceOperationException {
        categoryRepository.deleteById(name);
    }

    @Override
    public Category find(String name) throws ResourceOperationException {
        Optional<Category> byId = categoryRepository.findById(name);
        byId.orElseThrow(() -> ResourceOperationException.builder()
                .status(HttpStatus.NOT_FOUND)
                .resourceName("Category")
                .message("Category cannot be found!")
                .build());
        return byId.get();
    }
}
