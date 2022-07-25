package com.devjola.fashionblog.controller;


import com.devjola.fashionblog.dto.ModifyCategoryDto;
import com.devjola.fashionblog.model.Category;
import com.devjola.fashionblog.pagination_criteria.CategoryListPages;
import com.devjola.fashionblog.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/")
    public String createCategory(@RequestBody Category category) {
        categoryService.createCategory(category);
        return "Category Created";
    }

    @GetMapping("/{id}")
    public Category viewCategoryById(@PathVariable("id") Long id){
        return categoryService.viewCategoryById(id);
    }

    @GetMapping("/")
    public Page<Category> viewAllCategories(CategoryListPages categoryListPages) {
        return categoryService.viewAllCategories(categoryListPages);
    }


    @PatchMapping("/{id}")
    public Category modifyCategory(@PathVariable("id") Long id, @RequestBody ModifyCategoryDto categoryDto) {
        return categoryService.modifyCategory(id, categoryDto);
    }


    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable("id") Long id){
        categoryService.deleteCategory(id);
        return "category successfully deleted";
    }

}
