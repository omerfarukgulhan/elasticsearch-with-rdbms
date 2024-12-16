package com.ofg.elasticwithrdbms.repository;

import com.ofg.elasticwithrdbms.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
