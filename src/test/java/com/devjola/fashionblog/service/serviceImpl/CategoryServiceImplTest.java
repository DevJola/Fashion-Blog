package com.devjola.fashionblog.service.serviceImpl;

import com.devjola.fashionblog.dto.ModifyCategoryDto;
import com.devjola.fashionblog.enums.Role;
import com.devjola.fashionblog.exception.CategoryAlreadyExists;
import com.devjola.fashionblog.exception.ListIsEmpty;
import com.devjola.fashionblog.model.Category;
import com.devjola.fashionblog.model.User;
import com.devjola.fashionblog.pagination_criteria.CategoryListPages;
import com.devjola.fashionblog.repository.CategoryRepository;
import com.devjola.fashionblog.service.UserService;
import com.devjola.fashionblog.util.Util;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CategoryServiceImpl.class})
@ExtendWith(SpringExtension.class)
class CategoryServiceImplTest {
    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryServiceImpl categoryServiceImpl;

    @MockBean
    private HttpSession httpSession;

    @MockBean
    private UserService userService;

    @MockBean
    private Util util;

    @Test
    void testCreateCategory() {
        Category category = new Category();
        category.setId(123L);
        category.setName("Name");
        category.setPosts(new ArrayList<>());
        when(categoryRepository.existsByName((String) any())).thenReturn(true);
        when(categoryRepository.save((Category) any())).thenReturn(category);

        User user = new User();
        user.setComments(new ArrayList<>());
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setJoined_at(LocalDateTime.of(1, 1, 1, 1, 1));
        user.setLastName("Doe");
        user.setLikedItems(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setPosts(new ArrayList<>());
        user.setRole(Role.ADMIN);
        when(util.findUserById((Long) any())).thenReturn(user);
        when(util.getLoggedUserById()).thenReturn(123L);

        Category category1 = new Category();
        category1.setId(123L);
        category1.setName("Name");
        category1.setPosts(new ArrayList<>());
        assertThrows(CategoryAlreadyExists.class, () -> categoryServiceImpl.createCategory(category1));
        verify(categoryRepository).existsByName((String) any());
        verify(util).findUserById((Long) any());
        verify(util).getLoggedUserById();
    }


    @Test
    void testViewAllCategories() {
        when(categoryRepository.findAll()).thenReturn(new ArrayList<>());

        CategoryListPages categoryListPages = new CategoryListPages();
        categoryListPages.setPageNumber(10);
        categoryListPages.setPageSize(3);
        categoryListPages.setSortBy("Sort By");
        categoryListPages.setSortDirection(Sort.Direction.ASC);
        assertThrows(ListIsEmpty.class, () -> categoryServiceImpl.viewAllCategories(categoryListPages));
        verify(categoryRepository).findAll();
    }


    @Test
    void testViewCategoryById() {
        Category category = new Category();
        category.setId(123L);
        category.setName("Name");
        category.setPosts(new ArrayList<>());
        Optional<Category> ofResult = Optional.of(category);
        when(categoryRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(category, categoryServiceImpl.viewCategoryById(123L));
        verify(categoryRepository).findById((Long) any());
    }

    @Test
    void testModifyCategory() {
        Category category = new Category();
        category.setId(123L);
        category.setName("Name");
        category.setPosts(new ArrayList<>());
        Optional<Category> ofResult = Optional.of(category);

        Category category1 = new Category();
        category1.setId(123L);
        category1.setName("Name");
        category1.setPosts(new ArrayList<>());
        when(categoryRepository.save((Category) any())).thenReturn(category1);
        when(categoryRepository.findById((Long) any())).thenReturn(ofResult);

        User user = new User();
        user.setComments(new ArrayList<>());
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setJoined_at(LocalDateTime.of(1, 1, 1, 1, 1));
        user.setLastName("Doe");
        user.setLikedItems(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setPosts(new ArrayList<>());
        user.setRole(Role.ADMIN);
        when(util.findUserById((Long) any())).thenReturn(user);
        when(util.getLoggedUserById()).thenReturn(123L);
        assertSame(category1, categoryServiceImpl.modifyCategory(123L, new ModifyCategoryDto("Name")));
        verify(categoryRepository).save((Category) any());
        verify(categoryRepository).findById((Long) any());
        verify(util).findUserById((Long) any());
        verify(util).getLoggedUserById();
    }


    @Test
    void testDeleteCategory() {
        Category category = new Category();
        category.setId(123L);
        category.setName("Name");
        category.setPosts(new ArrayList<>());
        Optional<Category> ofResult = Optional.of(category);
        doNothing().when(categoryRepository).delete((Category) any());
        when(categoryRepository.findCategoryById((Long) any())).thenReturn(ofResult);

        User user = new User();
        user.setComments(new ArrayList<>());
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setJoined_at(LocalDateTime.of(1, 1, 1, 1, 1));
        user.setLastName("Doe");
        user.setLikedItems(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setPosts(new ArrayList<>());
        user.setRole(Role.ADMIN);
        when(util.findUserById((Long) any())).thenReturn(user);
        when(util.getLoggedUserById()).thenReturn(123L);
        categoryServiceImpl.deleteCategory(123L);
        verify(categoryRepository).findCategoryById((Long) any());
        verify(categoryRepository).delete((Category) any());
        verify(util).findUserById((Long) any());
        verify(util).getLoggedUserById();
    }
}

