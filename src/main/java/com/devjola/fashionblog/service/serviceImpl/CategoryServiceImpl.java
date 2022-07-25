package com.devjola.fashionblog.service.serviceImpl;

import com.devjola.fashionblog.pagination_criteria.CategoryListPages;
import com.devjola.fashionblog.util.Util;
import com.devjola.fashionblog.dto.ModifyCategoryDto;
import com.devjola.fashionblog.enums.Role;
import com.devjola.fashionblog.exception.*;
import com.devjola.fashionblog.model.Category;
import com.devjola.fashionblog.model.User;
import com.devjola.fashionblog.repository.CategoryRepository;
import com.devjola.fashionblog.service.CategoryService;
import com.devjola.fashionblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    public final HttpSession httpSession;
    public final UserService userService;
    public final CategoryRepository categoryRepository;
    private final Util util;



    @Override
    public void createCategory(Category category) {
        Long userId = util.getLoggedUserById();
        User user = util.findUserById(userId);

        if (!user.getRole().equals(Role.ADMIN)){
            throw new NotPermittedToPerformThisOperation("Only admins can upload post");
        }

        if(categoryRepository.existsByName(category.getName())){
            throw new CategoryAlreadyExists("This category already exists");
        }

        Category category1 = Category.builder()
                .name(category.getName())
                .build();
        categoryRepository.save(category1);

    }

    @Override
    public Page<Category> viewAllCategories(CategoryListPages categoryListPages) {
        List<Category> categoryList = categoryRepository.findAll();
        if (categoryList.isEmpty()){
            throw new ListIsEmpty("There are no categories yet");
        }
        Sort sort = Sort.by(categoryListPages.getSortDirection(), categoryListPages.getSortBy());
        Pageable pageable = PageRequest.of(categoryListPages.getPageNumber(), categoryListPages.getPageSize(), sort);

        return categoryRepository.findAll(pageable);

    }

    @Override
    public Category viewCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFound("Category was not found"));
    }


    @Override
    public Category modifyCategory(Long id, ModifyCategoryDto modifyCategoryDto) {
        Long userId = util.getLoggedUserById();
        User user = util.findUserById(userId);


        if (!user.getRole().equals(Role.ADMIN)){
            throw new NotPermittedToPerformThisOperation("Only admins can upload post");
        }

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFound("Category was not found"));

        if (modifyCategoryDto.getName() != null)
            category.setName(modifyCategoryDto.getName());

        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        Long userId = util.getLoggedUserById();
        User user = util.findUserById(userId);

        if (!user.getRole().equals(Role.ADMIN)){
            throw new NotPermittedToPerformThisOperation("Only admins can delete post");
        }

        Category category = categoryRepository.findCategoryById(id)
                .orElseThrow(() -> new CategoryNotFound("category not found"));

        categoryRepository.delete(category);
    }
}
