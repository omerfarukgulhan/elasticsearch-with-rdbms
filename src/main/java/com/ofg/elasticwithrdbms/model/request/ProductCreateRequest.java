package com.ofg.elasticwithrdbms.model.request;

import java.util.Set;
import java.util.UUID;

public record ProductCreateRequest(
        String name,
        String description,
        Double price,
        Set<UUID> categoryIds
) {
}
