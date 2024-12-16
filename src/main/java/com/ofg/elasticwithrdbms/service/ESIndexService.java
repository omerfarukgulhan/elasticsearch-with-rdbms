package com.ofg.elasticwithrdbms.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import com.ofg.elasticwithrdbms.model.entity.Product;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ESIndexService {
    private final ElasticsearchClient elasticsearchClient;
    private final ProductService productService;

    public ESIndexService(ElasticsearchClient elasticsearchClient, ProductService productService) {
        this.elasticsearchClient = elasticsearchClient;
        this.productService = productService;
    }

    public void indexAllProducts() throws IOException {
        List<Product> products = productService.getAllProductsForIndex();

        BulkRequest.Builder br = new BulkRequest.Builder();
        for (Product product : products) {
            br.operations(op -> op.index(idx -> idx
                    .index("products_index")
                    .id(product.getId().toString())
                    .document(product)
            ));
        }

        BulkResponse response = elasticsearchClient.bulk(br.build());

        if (response.errors()) {
            throw new RuntimeException("Failed to index some products: " + response.toString());
        }
    }
}