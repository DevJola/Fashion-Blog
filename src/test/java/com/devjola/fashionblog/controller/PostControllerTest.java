package com.devjola.fashionblog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.devjola.fashionblog.dto.ModifyPostDto;
import com.devjola.fashionblog.dto.UploadPostDto;
import com.devjola.fashionblog.enums.Role;
import com.devjola.fashionblog.model.Category;
import com.devjola.fashionblog.model.Post;
import com.devjola.fashionblog.model.User;
import com.devjola.fashionblog.pagination_criteria.PostListPages;
import com.devjola.fashionblog.repository.CategoryRepository;
import com.devjola.fashionblog.repository.PostRepository;
import com.devjola.fashionblog.repository.UserRepository;
import com.devjola.fashionblog.service.PostService;
import com.devjola.fashionblog.service.serviceImpl.PostServiceImpl;
import com.devjola.fashionblog.service.serviceImpl.UserServiceImpl;
import com.devjola.fashionblog.util.Util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {PostController.class, PostListPages.class})
@ExtendWith(SpringExtension.class)
class PostControllerTest {
    @Autowired
    private PostListPages postListPages;

    @Autowired
    private PostController postController;

    @MockBean
    private PostService postService;



    @Test
    void testUploadPost() {

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
        PostRepository postRepository = mock(PostRepository.class);
        when(postRepository.save((Post) any())).thenReturn(post);

        Category category1 = new Category();
        category1.setId(123L);
        category1.setName("Name");
        category1.setPosts(new ArrayList<>());
        Optional<Category> ofResult = Optional.of(category1);

        Category category2 = new Category();
        category2.setId(123L);
        category2.setName("Name");
        category2.setPosts(new ArrayList<>());
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        when(categoryRepository.save((Category) any())).thenReturn(category2);
        when(categoryRepository.findById((Long) any())).thenReturn(ofResult);

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
        Util util = mock(Util.class);
        when(util.findUserById((Long) any())).thenReturn(user1);
        when(util.getLoggedUserById()).thenReturn(123L);
        MockHttpSession httpSession = new MockHttpSession();
        UserRepository userRepository = mock(UserRepository.class);
        PostController postController = new PostController(new PostServiceImpl(postRepository, httpSession,
                new UserServiceImpl(userRepository, new MockHttpSession()), categoryRepository, util));
        UploadPostDto uploadPostDto = mock(UploadPostDto.class);
        when(uploadPostDto.getContent()).thenReturn("Not all who wander are lost");
        when(uploadPostDto.getImageUrl()).thenReturn("https://example.org/example");
        when(uploadPostDto.getTitle()).thenReturn("Dr");
        assertEquals("post uploaded", postController.uploadPost(123L, uploadPostDto));
        verify(postRepository).save((Post) any());
        verify(categoryRepository).save((Category) any());
        verify(categoryRepository).findById((Long) any());
        verify(util).findUserById((Long) any());
        verify(util).getLoggedUserById();
        verify(uploadPostDto).getContent();
        verify(uploadPostDto).getImageUrl();
        verify(uploadPostDto).getTitle();
    }


    @Test
    void testViewPostById() throws Exception {
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
        when(postService.viewPostById((Long) any())).thenReturn(post);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/posts/{id}", 123L);
        MockMvcBuilders.standaloneSetup(postController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"title\":\"Dr\",\"imageUrl\":\"https://example.org/example\",\"content\":\"Not all who wander are"
                                        + " lost\",\"dateCreated\":[1,1,1,1,1],\"dateModified\":[1,1,1,1,1],\"likedItems\":[],\"comments\":[]}"));
    }



    @Test
    void testModifyPost() {

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
        Util util = mock(Util.class);
        when(util.findUserById((Long) any())).thenReturn(user2);
        when(util.getLoggedUserById()).thenReturn(123L);
        MockHttpSession httpSession = new MockHttpSession();
        UserRepository userRepository = mock(UserRepository.class);
        PostController postController = new PostController(new PostServiceImpl(postRepository, httpSession,
                new UserServiceImpl(userRepository, new MockHttpSession()), mock(CategoryRepository.class), util));
        ModifyPostDto modifyPostDto = mock(ModifyPostDto.class);
        when(modifyPostDto.getContent()).thenReturn("Not all who wander are lost");
        when(modifyPostDto.getImageUrl()).thenReturn("https://example.org/example");
        when(modifyPostDto.getTitle()).thenReturn("Dr");
        assertSame(post1, postController.modifyPost(123L, modifyPostDto));
        verify(postRepository).save((Post) any());
        verify(postRepository).findById((Long) any());
        verify(util).findUserById((Long) any());
        verify(util).getLoggedUserById();
        verify(modifyPostDto, atLeast(1)).getContent();
        verify(modifyPostDto, atLeast(1)).getImageUrl();
        verify(modifyPostDto, atLeast(1)).getTitle();
    }


    @Test
    void testDeletePost() throws Exception {
        doNothing().when(postService).deletePost((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/posts/{postId}", 123L);
        MockMvcBuilders.standaloneSetup(postController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("post deleted"));
    }


    @Test
    void testViewPostByCategoryId() throws Exception {
        when(postService.ViewPostByCategory((Long) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/posts/postByCategoryId/{categoryId}", 123L);
        MockMvcBuilders.standaloneSetup(postController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}

