package com.ai.sys.controller;


import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.Category;
import com.ai.sys.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public @ResponseBody
    ResponseEntity<Category> findCategoryByName(@PathVariable("id") Long id) {
        try {
            Category category = categoryService.find(id);
            return ResponseEntity.ok(category);
        } catch (ResourceOperationException e) {
            return ResponseEntity
                    .status(e.getStatus())
                    .body(null);
        }
    }

    @PostMapping(value = "/add", consumes = {"application/json"})
    public ResponseEntity<String> addCategory(@RequestBody Category category) {
        try {
            categoryService.create(category);
            return ResponseEntity.ok().build();
        } catch (ResourceOperationException e) {
            log.debug("create category failed");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable("id") Long id) {
        try {
            categoryService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error(String.valueOf(e.getStackTrace()));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public List<Category> findAllCategorySet() {
        return categoryService.findAll();
    }
}
