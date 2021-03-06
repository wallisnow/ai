package com.ai.sys.controller;


import com.ai.sys.common.Response;
import com.ai.sys.model.entity.Algo;
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
        Category category = categoryService.find(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping(value = "/add", consumes = {"application/json"})
    public Response addCategory(@RequestBody Category category) {
        categoryService.create(category);
        return Response.httpOk();
    }

    @DeleteMapping("/{id}")
    public Response deleteCategory(@PathVariable("id") Long id) {
        categoryService.delete(id);
        return Response.httpOk();
    }

    @PutMapping(value = "/modify", consumes = {"application/json"})
    public Response modifyCategory(@RequestBody Category category) {
        categoryService.update(category);
        return Response.httpOk();
    }

    @GetMapping("/all")
    public List<Category> findAllCategorySet() {
        return categoryService.findAll();
    }
}
