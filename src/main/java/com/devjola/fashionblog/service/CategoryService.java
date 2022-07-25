package com.devjola.fashionblog.service;

import com.devjola.fashionblog.dto.ModifyCategoryDto;
import com.devjola.fashionblog.model.Category;
import com.devjola.fashionblog.pagination_criteria.CategoryListPages;
import org.springframework.data.domain.Page;

public interface CategoryService {
    void createCategory(Category category);

    Page<Category> viewAllCategories(CategoryListPages categoryListPages);

    Category modifyCategory(Long id, ModifyCategoryDto categoryDto);

    void deleteCategory(Long id);

    Category viewCategoryById(Long id);
}
