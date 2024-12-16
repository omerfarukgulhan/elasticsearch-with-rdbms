package com.ofg.elasticwithrdbms.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.ofg.elasticwithrdbms.model.entity.Category;
import com.ofg.elasticwithrdbms.model.entity.Product;
import com.ofg.elasticwithrdbms.model.request.ProductCreateRequest;
import com.ofg.elasticwithrdbms.model.response.CategoryResponse;
import com.ofg.elasticwithrdbms.model.response.ProductResponse;
import com.ofg.elasticwithrdbms.model.response.SearchRequest;
import com.ofg.elasticwithrdbms.repository.ProductRepository;
import com.ofg.elasticwithrdbms.util.ESUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ElasticsearchClient elasticsearchClient;

    public ProductService(ProductRepository productRepository,
                          CategoryService categoryService,
                          ElasticsearchClient elasticsearchClient) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.elasticsearchClient = elasticsearchClient;
    }

    public List<ProductResponse> getAllProducts(SearchRequest searchRequest) {
        try {
            Query query = ESUtil.buildQueryForFieldsAndValues(
                    searchRequest.getFieldName(),
                    searchRequest.getSearchValue()
            );

            SearchResponse<Product> response = elasticsearchClient.search(
                    s -> s.index("products_index").query(query),
                    Product.class
            );

            List<Product> products = response.hits().hits().stream()
                    .map(Hit::source)
                    .toList();

            return products.stream()
                    .map(this::mapToProductResponse)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException("Failed to fetch products", e);
        }
    }

    private ProductResponse mapToProductResponse(Product product) {
        Set<CategoryResponse> categoryResponses = product.getCategories().stream()
                .map(category -> new CategoryResponse(category.getId(), category.getName()))
                .collect(Collectors.toSet());

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                categoryResponses
        );
    }

    public ProductResponse addProduct(ProductCreateRequest productCreateRequest) {
        Product product = new Product();
        product.setName(productCreateRequest.name());
        product.setDescription(productCreateRequest.description());
        product.setPrice(productCreateRequest.price());

        Set<Category> categories = new HashSet<>();
        for (UUID categoryId : productCreateRequest.categoryIds()) {
            Category category = categoryService.getCategoryById(categoryId);
            if (category != null) {
                categories.add(category);
            }
        }
        product.setCategories(categories);
        productRepository.save(product);

        indexProductInElasticsearch(product);

        Set<CategoryResponse> categoryResponses = categories.stream()
                .map(category -> new CategoryResponse(category.getId(), category.getName()))
                .collect(Collectors.toSet());
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                categoryResponses
        );
    }

    private void indexProductInElasticsearch(Product product) {
        try {
            elasticsearchClient.index(i -> i
                    .index("products")
                    .id(product.getId().toString())
                    .document(product)
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to index product", e);
        }
    }

    public List<Product> getAllProductsForIndex() {
        return productRepository.findAll();
    }
}
