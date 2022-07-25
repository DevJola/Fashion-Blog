package com.devjola.fashionblog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.devjola.fashionblog.enums.Role;
import com.devjola.fashionblog.model.Category;
import com.devjola.fashionblog.model.Comment;
import com.devjola.fashionblog.model.Post;
import com.devjola.fashionblog.model.User;
import com.devjola.fashionblog.repository.CommentRepository;
import com.devjola.fashionblog.repository.PostRepository;
import com.devjola.fashionblog.repository.UserRepository;
import com.devjola.fashionblog.service.CommentService;
import com.devjola.fashionblog.service.serviceImpl.CommentServiceImpl;
import com.devjola.fashionblog.service.serviceImpl.UserServiceImpl;
import com.devjola.fashionblog.util.Util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CommentController.class})
@ExtendWith(SpringExtension.class)
class CommentControllerTest {
    @Autowired
    private CommentController commentController;

    @MockBean
    private CommentService commentService;


    @Test
    void testMakeComment() {

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

        Category category1 = new Category();
        category1.setId(123L);
        category1.setName("Name");
        category1.setPosts(new ArrayList<>());

        User user1 = new User();
        user1.setComments(new ArrayList<>());
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setJoined_at(LocalDateTime.of(1, 1, 1, 1, 1));
        user1.setLastName("Doe");
        user1.setLikedItems(new ArrayList<>());
        user1.setPassword("iloveyou");
        user1.setPosts(new ArrayList<>());
        user1.setRole(Role.ADMIN);

        Post post1 = new Post();
        post1.setCategory(category1);
        post1.setComments(new ArrayList<>());
        post1.setContent("Not all who wander are lost");
        post1.setDateCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        post1.setDateModified(LocalDateTime.of(1, 1, 1, 1, 1));
        post1.setId(123L);
        post1.setImageUrl("https://example.org/example");
        post1.setLikedItems(new ArrayList<>());
        post1.setTitle("Dr");
        post1.setUser(user1);
        PostRepository postRepository = mock(PostRepository.class);
        when(postRepository.save((Post) any())).thenReturn(post1);
        when(postRepository.findById((Long) any())).thenReturn(ofResult);

        Category category2 = new Category();
        category2.setId(123L);
        category2.setName("Name");
        category2.setPosts(new ArrayList<>());

        User user2 = new User();
        user2.setComments(new ArrayList<>());
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(123L);
        user2.setJoined_at(LocalDateTime.of(1, 1, 1, 1, 1));
        user2.setLastName("Doe");
        user2.setLikedItems(new ArrayList<>());
        user2.setPassword("iloveyou");
        user2.setPosts(new ArrayList<>());
        user2.setRole(Role.ADMIN);

        Post post2 = new Post();
        post2.setCategory(category2);
        post2.setComments(new ArrayList<>());
        post2.setContent("Not all who wander are lost");
        post2.setDateCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        post2.setDateModified(LocalDateTime.of(1, 1, 1, 1, 1));
        post2.setId(123L);
        post2.setImageUrl("https://example.org/example");
        post2.setLikedItems(new ArrayList<>());
        post2.setTitle("Dr");
        post2.setUser(user2);

        User user3 = new User();
        user3.setComments(new ArrayList<>());
        user3.setEmail("jane.doe@example.org");
        user3.setFirstName("Jane");
        user3.setId(123L);
        user3.setJoined_at(LocalDateTime.of(1, 1, 1, 1, 1));
        user3.setLastName("Doe");
        user3.setLikedItems(new ArrayList<>());
        user3.setPassword("iloveyou");
        user3.setPosts(new ArrayList<>());
        user3.setRole(Role.ADMIN);

        Comment comment = new Comment();
        comment.setContent("Not all who wander are lost");
        comment.setDateCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        comment.setDateModified(LocalDateTime.of(1, 1, 1, 1, 1));
        comment.setId(123L);
        comment.setPost(post2);
        comment.setUser(user3);
        CommentRepository commentRepository = mock(CommentRepository.class);
        when(commentRepository.save((Comment) any())).thenReturn(comment);

        User user4 = new User();
        user4.setComments(new ArrayList<>());
        user4.setEmail("jane.doe@example.org");
        user4.setFirstName("Jane");
        user4.setId(123L);
        user4.setJoined_at(LocalDateTime.of(1, 1, 1, 1, 1));
        user4.setLastName("Doe");
        user4.setLikedItems(new ArrayList<>());
        user4.setPassword("iloveyou");
        user4.setPosts(new ArrayList<>());
        user4.setRole(Role.VISITOR);
        Util util = mock(Util.class);
        when(util.findUserById((Long) any())).thenReturn(user4);
        when(util.getLoggedUserById()).thenReturn(123L);
        UserRepository userRepository = mock(UserRepository.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository, new MockHttpSession());

        CommentController commentController = new CommentController(
                new CommentServiceImpl(userService, postRepository, commentRepository, new MockHttpSession(), util));

        Category category3 = new Category();
        category3.setId(123L);
        category3.setName("Name");
        category3.setPosts(new ArrayList<>());

        User user5 = new User();
        user5.setComments(new ArrayList<>());
        user5.setEmail("jane.doe@example.org");
        user5.setFirstName("Jane");
        user5.setId(123L);
        user5.setJoined_at(LocalDateTime.of(1, 1, 1, 1, 1));
        user5.setLastName("Doe");
        user5.setLikedItems(new ArrayList<>());
        user5.setPassword("iloveyou");
        user5.setPosts(new ArrayList<>());
        user5.setRole(Role.ADMIN);

        Post post3 = new Post();
        post3.setCategory(category3);
        post3.setComments(new ArrayList<>());
        post3.setContent("Not all who wander are lost");
        post3.setDateCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        post3.setDateModified(LocalDateTime.of(1, 1, 1, 1, 1));
        post3.setId(123L);
        post3.setImageUrl("https://example.org/example");
        post3.setLikedItems(new ArrayList<>());
        post3.setTitle("Dr");
        post3.setUser(user5);

        User user6 = new User();
        user6.setComments(new ArrayList<>());
        user6.setEmail("jane.doe@example.org");
        user6.setFirstName("Jane");
        user6.setId(123L);
        user6.setJoined_at(LocalDateTime.of(1, 1, 1, 1, 1));
        user6.setLastName("Doe");
        user6.setLikedItems(new ArrayList<>());
        user6.setPassword("iloveyou");
        user6.setPosts(new ArrayList<>());
        user6.setRole(Role.ADMIN);

        Comment comment1 = new Comment();
        comment1.setContent("Not all who wander are lost");
        comment1.setDateCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        comment1.setDateModified(LocalDateTime.of(1, 1, 1, 1, 1));
        comment1.setId(123L);
        comment1.setPost(post3);
        comment1.setUser(user6);
        assertEquals("Comment Saved", commentController.makeComment(123L, comment1));
        verify(postRepository).save((Post) any());
        verify(postRepository).findById((Long) any());
        verify(commentRepository).save((Comment) any());
        verify(util).findUserById((Long) any());
        verify(util).getLoggedUserById();
    }


    @Test
    void testPrintNumberOfComments() throws Exception {
        when(commentService.printNumberOfComments((Long) any())).thenReturn(10);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/comments/noOfComments/{id}",
                123L);
        MockMvcBuilders.standaloneSetup(commentController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("10"));
    }


    @Test
    void testViewPostComment() throws Exception {
        when(commentService.viewPostComments((Long) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/comments/{id}", 123L);
        MockMvcBuilders.standaloneSetup(commentController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

}

