package com.devjola.fashionblog.service.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.devjola.fashionblog.enums.Role;
import com.devjola.fashionblog.exception.NotPermittedToPerformThisOperation;
import com.devjola.fashionblog.exception.PostNotFound;
import com.devjola.fashionblog.model.Category;
import com.devjola.fashionblog.model.Comment;
import com.devjola.fashionblog.model.LikedItems;
import com.devjola.fashionblog.model.Post;
import com.devjola.fashionblog.model.User;
import com.devjola.fashionblog.repository.PostRepository;
import com.devjola.fashionblog.service.UserService;
import com.devjola.fashionblog.util.Util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {LikedItemsServiceImpl.class})
@ExtendWith(SpringExtension.class)
class LikedItemsServiceImplTest {
    @MockBean
    private HttpSession httpSession;

    @Autowired
    private LikedItemsServiceImpl likedItemsServiceImpl;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private Util util;


    @Test
    void testLikePost() {
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
        assertThrows(NotPermittedToPerformThisOperation.class, () -> likedItemsServiceImpl.likePost(123L));
        verify(util).findUserById((Long) any());
        verify(util).getLoggedUserById();
    }


    @Test
    void testNoOfLikesPerPost() {
        Category category = new Category();
        category.setId(123L);
        category.setName("Name");
        category.setPosts(new ArrayList<>());

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

        Post post = new Post();
        post.setCategory(category);
        post.setComments(new ArrayList<>());
        post.setContent("Not all who wander are lost");
        post.setDateCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        post.setDateModified(LocalDateTime.of(1, 1, 1, 1, 1));
        post.setId(123L);
        post.setImageUrl("https://example.org/example");
        post.setLikedItems(new ArrayList<>());
        post.setTitle("Dr");
        post.setUser(user);
        Optional<Post> ofResult = Optional.of(post);
        when(postRepository.findById((Long) any())).thenReturn(ofResult);
        assertEquals(0, likedItemsServiceImpl.noOfLikesPerPost(123L));
        verify(postRepository).findById((Long) any());
    }


    @Test
    void itShouldThrowPostNotFoundException() {
        when(postRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(PostNotFound.class, () -> likedItemsServiceImpl.noOfLikesPerPost(123L));
        verify(postRepository).findById((Long) any());
    }


    @Test
    void itShouldThrowNotPermittedToPerformThisOperation() {
        when(postRepository.findById((Long) any()))
                .thenThrow(new NotPermittedToPerformThisOperation("Not all who wander are lost"));
        assertThrows(NotPermittedToPerformThisOperation.class, () -> likedItemsServiceImpl.noOfLikesPerPost(123L));
        verify(postRepository).findById((Long) any());
    }
}

