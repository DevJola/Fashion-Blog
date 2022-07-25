package com.devjola.fashionblog.service.serviceImpl;

import com.devjola.fashionblog.enums.Role;
import com.devjola.fashionblog.exception.NotPermittedToPerformThisOperation;
import com.devjola.fashionblog.exception.PostNotFound;
import com.devjola.fashionblog.model.Category;
import com.devjola.fashionblog.model.Comment;
import com.devjola.fashionblog.model.Post;
import com.devjola.fashionblog.model.User;
import com.devjola.fashionblog.repository.CommentRepository;
import com.devjola.fashionblog.repository.PostRepository;
import com.devjola.fashionblog.service.UserService;
import com.devjola.fashionblog.util.Util;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CommentServiceImpl.class})
@ExtendWith(SpringExtension.class)
class CommentServiceImplTest {
    @MockBean
    private CommentRepository commentRepository;

    @Autowired
    private CommentServiceImpl commentServiceImpl;

    @MockBean
    private HttpSession httpSession;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private Util util;


    @Test
    void testMakeComment() {
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

        Category category = new Category();
        category.setId(123L);
        category.setName("Name");
        category.setPosts(new ArrayList<>());

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
        post.setUser(user1);

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

        Comment comment = new Comment();
        comment.setContent("Not all who wander are lost");
        comment.setDateCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        comment.setDateModified(LocalDateTime.of(1, 1, 1, 1, 1));
        comment.setId(123L);
        comment.setPost(post);
        comment.setUser(user2);
        assertThrows(NotPermittedToPerformThisOperation.class, () -> commentServiceImpl.makeComment(123L, comment));
        verify(util).findUserById((Long) any());
        verify(util).getLoggedUserById();
    }


    @Test
    void testViewPostComments() {
        when(postRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(PostNotFound.class, () -> commentServiceImpl.viewPostComments(123L));
        verify(postRepository).findById((Long) any());
    }


    @Test
    void testPrintNumberOfComments() {
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
        assertEquals(0, commentServiceImpl.printNumberOfComments(123L));
        verify(postRepository).findById((Long) any());
    }


    @Test
    void itShouldThrowPostNotFoundException() {
        when(postRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(PostNotFound.class, () -> commentServiceImpl.printNumberOfComments(123L));
        verify(postRepository).findById((Long) any());
    }


    @Test
    void itShouldThrowNotPermittedToPerformThisOperation() {
        when(postRepository.findById((Long) any()))
                .thenThrow(new NotPermittedToPerformThisOperation("Not all who wander are lost"));
        assertThrows(NotPermittedToPerformThisOperation.class, () -> commentServiceImpl.printNumberOfComments(123L));
        verify(postRepository).findById((Long) any());
    }
}

