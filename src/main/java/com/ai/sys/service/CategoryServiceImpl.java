package com.ai.sys.service;

import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.Category;
import com.ai.sys.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    public void delete(Long id) throws ResourceOperationException {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category find(Long id) throws ResourceOperationException {
        Optional<Category> byId = categoryRepository.findById(id);
        byId.orElseThrow(() -> ResourceOperationException.builder()
                .status(HttpStatus.NOT_FOUND)
                .resourceName("Category")
                .message("Category cannot be found!")
                .build());
        return byId.get();
    }

    @Override
    public Optional<Category> findByName(String name) throws ResourceOperationException {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> findAll() throws ResourceOperationException {
        return new ArrayList<>(categoryRepository.findAll());
    }

    @Override
    public void update(Category category) throws ResourceOperationException {
        boolean exists = categoryRepository.existsById(category.getId());
        if (exists) {
            categoryRepository.save(category);
        } else {
            throw ResourceOperationException.builder()
                    .resourceName("Category")
                    .status(HttpStatus.NOT_FOUND)
                    .message("Category cannot be found")
                    .build();
        }
    }
}
