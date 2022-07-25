package com.devjola.fashionblog.service.serviceImpl;

import com.devjola.fashionblog.dto.ModifyPostDto;
import com.devjola.fashionblog.dto.UploadPostDto;
import com.devjola.fashionblog.dto.ViewPostsDto;
import com.devjola.fashionblog.enums.Role;
import com.devjola.fashionblog.exception.ListIsEmpty;
import com.devjola.fashionblog.exception.NotPermittedToPerformThisOperation;
import com.devjola.fashionblog.exception.PostNotFound;
import com.devjola.fashionblog.model.*;
import com.devjola.fashionblog.pagination_criteria.PostListPages;
import com.devjola.fashionblog.repository.CategoryRepository;
import com.devjola.fashionblog.repository.PostRepository;
import com.devjola.fashionblog.service.UserService;
import com.devjola.fashionblog.util.Util;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {PostServiceImpl.class, PostListPages.class})
@ExtendWith(SpringExtension.class)
class PostServiceImplTest {
    @Autowired
    private PostListPages postListPages;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private HttpSession httpSession;

    @MockBean
    private PostRepository postRepository;

    @Autowired
    private PostServiceImpl postServiceImpl;

    @MockBean
    private UserService userService;

    @MockBean
    private Util util;

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
        when(util.findUserById((Long) any())).thenReturn(user1);
        when(util.getLoggedUserById()).thenReturn(123L);
        UploadPostDto uploadPostDto = mock(UploadPostDto.class);
        when(uploadPostDto.getContent()).thenReturn("Not all who wander are lost");
        when(uploadPostDto.getImageUrl()).thenReturn("https://example.org/example");
        when(uploadPostDto.getTitle()).thenReturn("Dr");
        postServiceImpl.uploadPost(uploadPostDto, 123L);
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
    void itShouldThrowListIsEmptyException() {
        when(postRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(ListIsEmpty.class, () -> postServiceImpl.viewAllPosts(postListPages));
        verify(postRepository).findAll();
    }

    @Test
    void testViewAllPosts() {
        Category category = new Category();
        category.setId(123L);
        category.setName("There are no posts yet");
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

        ArrayList<Post> postList = new ArrayList<>();
        postList.add(post);
        PageImpl<Post> pageImpl = new PageImpl<>(new ArrayList<>());
        when(postRepository.findAll((Pageable) any())).thenReturn(pageImpl);
        when(postRepository.findAll()).thenReturn(postList);
        Page<Post> actualViewAllPostsResult = postServiceImpl.viewAllPosts(postListPages);
        assertSame(pageImpl, actualViewAllPostsResult);
        assertTrue(actualViewAllPostsResult.toList().isEmpty());
        verify(postRepository).findAll();
        verify(postRepository).findAll((Pageable) any());
    }

    /**
     * Method under test: {@link PostServiceImpl#viewAllPosts(PostListPages)}
     */
    @Test
    void itShouldThrowNotPermittedToPerformThisOperation() {
        Category category = new Category();
        category.setId(123L);
        category.setName("There are no posts yet");
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

        ArrayList<Post> postList = new ArrayList<>();
        postList.add(post);
        when(postRepository.findAll((Pageable) any()))
                .thenThrow(new NotPermittedToPerformThisOperation("Not all who wander are lost"));
        when(postRepository.findAll()).thenReturn(postList);
        assertThrows(NotPermittedToPerformThisOperation.class, () -> postServiceImpl.viewAllPosts(postListPages));
        verify(postRepository).findAll();
        verify(postRepository).findAll((Pageable) any());
    }

    @Test
    void testViewPostById() {
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
        assertSame(post, postServiceImpl.viewPostById(123L));
        verify(postRepository).findById((Long) any());
    }

    @Test
    void itShouldThrowPostNotFoundException() {
        when(postRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(PostNotFound.class, () -> postServiceImpl.viewPostById(123L));
        verify(postRepository).findById((Long) any());
    }

    @Test
    void itShouldThrowNotPermittedForThisOperation() {
        when(postRepository.findById((Long) any()))
                .thenThrow(new NotPermittedToPerformThisOperation("Not all who wander are lost"));
        assertThrows(NotPermittedToPerformThisOperation.class, () -> postServiceImpl.viewPostById(123L));
        verify(postRepository).findById((Long) any());
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
        when(util.findUserById((Long) any())).thenReturn(user2);
        when(util.getLoggedUserById()).thenReturn(123L);
        ModifyPostDto modifyPostDto = mock(ModifyPostDto.class);
        when(modifyPostDto.getContent()).thenReturn("Not all who wander are lost");
        when(modifyPostDto.getImageUrl()).thenReturn("https://example.org/example");
        when(modifyPostDto.getTitle()).thenReturn("Dr");
        assertSame(post1, postServiceImpl.modifyPost(123L, modifyPostDto));
        verify(postRepository).save((Post) any());
        verify(postRepository).findById((Long) any());
        verify(util).findUserById((Long) any());
        verify(util).getLoggedUserById();
        verify(modifyPostDto, atLeast(1)).getContent();
        verify(modifyPostDto, atLeast(1)).getImageUrl();
        verify(modifyPostDto, atLeast(1)).getTitle();
    }

    @Test
    void shouldThrowPostNotFound() {
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
        when(util.findUserById((Long) any())).thenReturn(user2);
        when(util.getLoggedUserById()).thenReturn(123L);
        ModifyPostDto modifyPostDto = mock(ModifyPostDto.class);
        when(modifyPostDto.getContent()).thenThrow(new PostNotFound("Not all who wander are lost"));
        when(modifyPostDto.getImageUrl()).thenThrow(new PostNotFound("Not all who wander are lost"));
        when(modifyPostDto.getTitle()).thenThrow(new PostNotFound("Not all who wander are lost"));
        assertThrows(PostNotFound.class, () -> postServiceImpl.modifyPost(123L, modifyPostDto));
        verify(postRepository).findById((Long) any());
        verify(util).findUserById((Long) any());
        verify(util).getLoggedUserById();
        verify(modifyPostDto).getTitle();
    }


    @Test
    void testModifyPost3() {
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
        Post post = mock(Post.class);
        doNothing().when(post).setCategory((Category) any());
        doNothing().when(post).setComments((List<Comment>) any());
        doNothing().when(post).setContent((String) any());
        doNothing().when(post).setDateCreated((LocalDateTime) any());
        doNothing().when(post).setDateModified((LocalDateTime) any());
        doNothing().when(post).setId((Long) any());
        doNothing().when(post).setImageUrl((String) any());
        doNothing().when(post).setLikedItems((List<LikedItems>) any());
        doNothing().when(post).setTitle((String) any());
        doNothing().when(post).setUser((User) any());
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
        when(util.findUserById((Long) any())).thenReturn(user2);
        when(util.getLoggedUserById()).thenReturn(123L);
        ModifyPostDto modifyPostDto = mock(ModifyPostDto.class);
        when(modifyPostDto.getContent()).thenReturn("Not all who wander are lost");
        when(modifyPostDto.getImageUrl()).thenReturn("https://example.org/example");
        when(modifyPostDto.getTitle()).thenReturn("Dr");
        assertSame(post1, postServiceImpl.modifyPost(123L, modifyPostDto));
        verify(postRepository).save((Post) any());
        verify(postRepository).findById((Long) any());
        verify(post).setCategory((Category) any());
        verify(post).setComments((List<Comment>) any());
        verify(post, atLeast(1)).setContent((String) any());
        verify(post).setDateCreated((LocalDateTime) any());
        verify(post).setDateModified((LocalDateTime) any());
        verify(post).setId((Long) any());
        verify(post, atLeast(1)).setImageUrl((String) any());
        verify(post).setLikedItems((List<LikedItems>) any());
        verify(post, atLeast(1)).setTitle((String) any());
        verify(post).setUser((User) any());
        verify(util).findUserById((Long) any());
        verify(util).getLoggedUserById();
        verify(modifyPostDto, atLeast(1)).getContent();
        verify(modifyPostDto, atLeast(1)).getImageUrl();
        verify(modifyPostDto, atLeast(1)).getTitle();
    }

    @Test
    void testDeletePost() {
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
        doNothing().when(postRepository).delete((Post) any());
        when(postRepository.findById((Long) any())).thenReturn(ofResult);

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
        when(util.findUserById((Long) any())).thenReturn(user1);
        when(util.getLoggedUserById()).thenReturn(123L);
        postServiceImpl.deletePost(123L);
        verify(postRepository).findById((Long) any());
        verify(postRepository).delete((Post) any());
        verify(util).findUserById((Long) any());
        verify(util).getLoggedUserById();
    }


    @Test
    void testViewPostByCategory() {
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

        ArrayList<Post> postList = new ArrayList<>();
        postList.add(post);

        Category category1 = new Category();
        category1.setId(123L);
        category1.setName("Name");
        category1.setPosts(postList);
        Optional<Category> ofResult = Optional.of(category1);
        when(categoryRepository.findCategoryById((Long) any())).thenReturn(ofResult);
        List<ViewPostsDto> actualViewPostByCategoryResult = postServiceImpl.ViewPostByCategory(123L);
        assertEquals(1, actualViewPostByCategoryResult.size());
        ViewPostsDto getResult = actualViewPostByCategoryResult.get(0);
        assertEquals(0, getResult.getComments());
        assertEquals("Dr", getResult.getTitle());
        assertEquals(0, getResult.getLikes());
        assertEquals("https://example.org/example", getResult.getImageUrl());
        assertEquals("Not all who wander are lost", getResult.getContent());
        verify(categoryRepository).findCategoryById((Long) any());
    }

    @Test
    void itShouldThrowCategoryNotFoundException() {
        when(categoryRepository.findCategoryById((Long) any())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> postServiceImpl.ViewPostByCategory(123L));
        verify(categoryRepository).findCategoryById((Long) any());
    }


    @Test
    void itShouldThrowPermissionNOtGrantedToPerformThisOperation() {
        when(categoryRepository.findCategoryById((Long) any()))
                .thenThrow(new NotPermittedToPerformThisOperation("Not all who wander are lost"));
        assertThrows(NotPermittedToPerformThisOperation.class, () -> postServiceImpl.ViewPostByCategory(123L));
        verify(categoryRepository).findCategoryById((Long) any());
    }
}
