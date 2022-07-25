package com.devjola.fashionblog.repository;

import com.devjola.fashionblog.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByName(String category);
    Optional<Category> findCategoryById(Long id);
}
