package com.ai.sys.controller;


import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.Algo;
import com.ai.sys.model.entity.Category;
import com.ai.sys.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{name}")
    public @ResponseBody
    ResponseEntity<Category> findCategoryByName(@PathVariable("name") String name) {
        try {
            Category category = categoryService.find(name);
            return ResponseEntity.ok(category);
        } catch (ResourceOperationException e) {
            return ResponseEntity
                    .status(e.getStatus())
                    .body(null);
        }
    }

    @PostMapping(value = "/", consumes = {"application/json"})
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

    @DeleteMapping("/{name}")
    public ResponseEntity<HttpStatus> deleteDataSet(@PathVariable("name") String name) {
        try {
            categoryService.delete(name);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error(e.getStackTrace());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
