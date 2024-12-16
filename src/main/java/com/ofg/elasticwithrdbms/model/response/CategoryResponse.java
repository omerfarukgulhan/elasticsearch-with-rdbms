package com.ofg.elasticwithrdbms.model.response;

import java.util.UUID;

public record CategoryResponse(
        UUID id,
        String name
) {
}
