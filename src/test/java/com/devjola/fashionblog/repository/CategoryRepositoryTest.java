package com.devjola.fashionblog.repository;

import com.devjola.fashionblog.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepositoryUnderTest;

    @Test
    void itChecksIfCategoryExistsByName() {
        //given
        String name = "shoes";
        Category category = Category.builder()
                .posts(new ArrayList<>())
                .name("shoes")
                .build();
        categoryRepositoryUnderTest.save(category);

        //when
        boolean expected = categoryRepositoryUnderTest
                                    .existsByName(name);

        //then
        assertThat(expected).isTrue();
    }


    @Test
    void itChecksIfCategoryDoesNotExistsByName() {
        //given
        String name = "shoes";

        //when
        boolean expected = categoryRepositoryUnderTest
                .existsByName(name);

        //then
        assertThat(expected).isFalse();
    }


}