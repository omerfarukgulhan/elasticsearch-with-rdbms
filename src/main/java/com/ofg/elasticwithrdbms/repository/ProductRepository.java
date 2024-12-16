package com.ofg.elasticwithrdbms.repository;

import com.ofg.elasticwithrdbms.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
