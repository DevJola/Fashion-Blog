package com.devjola.fashionblog.repository;

import com.devjola.fashionblog.enums.Role;
import com.devjola.fashionblog.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private  UserRepository userRepositoryUnderTest;


    @Test
    void itChecksIfUserExistsByEmail() {
        //given
        String email = "paulakerejola@gmail.com";
        User user = User.builder()
                .id(1L)
                .email("paulakerejola@gmail.com")
                .firstName("Paul")
                .lastName("Akerejola")
                .password("1234")
                .role(Role.VISITOR)
                .comments(new ArrayList<>())
                .likedItems(new ArrayList<>())
                .joined_at(LocalDateTime.now())
                .posts(new ArrayList<>())
                .build();

        userRepositoryUnderTest.save(user);

        //when
        boolean expected = userRepositoryUnderTest.existsByEmail(email);
        //then

        assertThat(expected).isTrue();
    }

    @Test
    void itChecksIfUserDoesNotExistsByEmail() {
        //given
        String email = "paulakerejola@gmail.com";
        //when
        boolean expected = userRepositoryUnderTest.existsByEmail(email);
        //then

        assertThat(expected).isFalse();
    }

    @Test
    void itChecksIfUserCanBeFoundByEmail() {
        //given
        String email = "paulakerejola@gmail.com";
        User user = User.builder()
                .email("paulakerejola@gmail.com")
                .firstName("Paul")
                .lastName("Akerejola")
                .password("1234")
                .role(Role.VISITOR)
                .comments(new ArrayList<>())
                .likedItems(new ArrayList<>())
                .joined_at(LocalDateTime.now())
                .posts(new ArrayList<>())
                .build();

        userRepositoryUnderTest.save(user);

        //when
        Optional<User> user1 = userRepositoryUnderTest.findByEmail(email);

        //then
        assertThat(user1).isPresent();
    }


    @Test
    void itChecksIfUserCanNotBeFoundByEmail() {
        //given
        String email = "paulakerejola@gmail.com";

        //when
        Optional<User> user1 = userRepositoryUnderTest.findByEmail(email);

        //then
        assertThat(user1).isEmpty();
    }
}