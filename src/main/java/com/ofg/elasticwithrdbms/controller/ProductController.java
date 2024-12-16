package com.ofg.elasticwithrdbms.controller;

import com.ofg.elasticwithrdbms.model.request.CategoryCreateRequest;
import com.ofg.elasticwithrdbms.model.request.ProductCreateRequest;
import com.ofg.elasticwithrdbms.model.response.CategoryResponse;
import com.ofg.elasticwithrdbms.model.response.ProductResponse;
import com.ofg.elasticwithrdbms.model.response.SearchRequest;
import com.ofg.elasticwithrdbms.service.CategoryService;
import com.ofg.elasticwithrdbms.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/search")
    public List<ProductResponse> searchProducts(@RequestBody SearchRequest searchRequest) throws IOException {
        return productService.getAllProducts(searchRequest);
    }

    @PostMapping
    public ProductResponse addProduct(@RequestBody ProductCreateRequest productCreateRequest) {
        return productService.addProduct(productCreateRequest);
    }
}
