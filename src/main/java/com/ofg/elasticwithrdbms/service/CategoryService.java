package com.ofg.elasticwithrdbms.service;

import com.ofg.elasticwithrdbms.model.entity.Category;
import com.ofg.elasticwithrdbms.model.request.CategoryCreateRequest;
import com.ofg.elasticwithrdbms.model.response.CategoryResponse;
import com.ofg.elasticwithrdbms.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category getCategoryById(UUID categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }

    public CategoryResponse addCategory(CategoryCreateRequest categoryCreateRequest) {
        Category category = new Category();
        category.setName(categoryCreateRequest.name());
        category = categoryRepository.save(category);
        return new CategoryResponse(category.getId(), category.getName());
    }

}
