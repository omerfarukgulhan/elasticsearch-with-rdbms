package com.ofg.elasticwithrdbms.controller;

import com.ofg.elasticwithrdbms.model.request.CategoryCreateRequest;
import com.ofg.elasticwithrdbms.model.response.CategoryResponse;
import com.ofg.elasticwithrdbms.service.CategoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public CategoryResponse addCategory(@RequestBody CategoryCreateRequest categoryCreateRequest) {
        return categoryService.addCategory(categoryCreateRequest);
    }
}
