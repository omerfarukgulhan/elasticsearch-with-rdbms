package com.ofg.elasticwithrdbms.model.response;

import com.ofg.elasticwithrdbms.model.entity.Category;

import java.util.Set;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        String description,
        Double price,
        Set<CategoryResponse> categories
) {
}
