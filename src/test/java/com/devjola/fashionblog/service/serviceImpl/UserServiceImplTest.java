package com.devjola.fashionblog.service.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.devjola.fashionblog.dto.UserLoginDto;
import com.devjola.fashionblog.dto.UserSignUpDto;
import com.devjola.fashionblog.enums.Role;
import com.devjola.fashionblog.exception.UserAlreadyExists;
import com.devjola.fashionblog.exception.UserNotFound;
import com.devjola.fashionblog.model.Comment;
import com.devjola.fashionblog.model.LikedItems;
import com.devjola.fashionblog.model.Post;
import com.devjola.fashionblog.model.User;
import com.devjola.fashionblog.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserServiceImplTest {
    @MockBean
    private HttpSession httpSession;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    void itShouldThrowUserAlreadyExistsExceptionIfUsersEmailAlreadyExistsOnSignUp() {
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
        when(userRepository.existsByEmail((String) any())).thenReturn(true);
        when(userRepository.save((User) any())).thenReturn(user);
        assertThrows(UserAlreadyExists.class,
                () -> userServiceImpl.signUp(new UserSignUpDto("jane.doe@example.org", "iloveyou", "Jane", "Doe", Role.ADMIN)));
        verify(userRepository).existsByEmail((String) any());
    }

    @Test
    void testSignUp() {
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
        when(userRepository.existsByEmail((String) any())).thenReturn(false);
        when(userRepository.save((User) any())).thenReturn(user);
        User actualSignUpResult = userServiceImpl
                .signUp(new UserSignUpDto("jane.doe@example.org", "iloveyou", "Jane", "Doe", Role.ADMIN));
        assertNull(actualSignUpResult.getComments());
        assertEquals(Role.VISITOR, actualSignUpResult.getRole());
        assertNull(actualSignUpResult.getPosts());
        assertEquals("iloveyou", actualSignUpResult.getPassword());
        assertNull(actualSignUpResult.getLikedItems());
        assertEquals("Doe", actualSignUpResult.getLastName());
        assertNull(actualSignUpResult.getJoined_at());
        assertNull(actualSignUpResult.getId());
        assertEquals("Jane", actualSignUpResult.getFirstName());
        assertEquals("jane.doe@example.org", actualSignUpResult.getEmail());
        verify(userRepository).existsByEmail((String) any());
        verify(userRepository).save((User) any());
    }

    @Test
    void itShouldThrowUserNotFoundWhenAUserWhoHasNotSignedUpTriesToLogin() {
        when(userRepository.existsByEmail((String) any())).thenThrow(new UserNotFound("Not all who wander are lost"));
        when(userRepository.save((User) any())).thenThrow(new UserNotFound("Not all who wander are lost"));
        assertThrows(UserNotFound.class,
                () -> userServiceImpl.signUp(new UserSignUpDto("jane.doe@example.org", "iloveyou", "Jane", "Doe", Role.ADMIN)));
        verify(userRepository).existsByEmail((String) any());
    }

    @Test
    void checksThatUserEmailAndPasswordAreCorrect() {
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
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByEmail((String) any())).thenReturn(ofResult);
        doNothing().when(httpSession).setAttribute((String) any(), (Object) any());
        UserLoginDto userLoginDto = mock(UserLoginDto.class);
        when(userLoginDto.getEmail()).thenReturn("jane.doe@example.org");
        when(userLoginDto.getPassword()).thenReturn("iloveyou");
        assertEquals("Successfully Signed In", userServiceImpl.login(userLoginDto));
        verify(userRepository).findByEmail((String) any());
        verify(httpSession, atLeast(1)).setAttribute((String) any(), (Object) any());
        verify(userLoginDto).getEmail();
        verify(userLoginDto).getPassword();
    }

    @Test
    void isShouldThrowUserAlreadyExistsIfUserTriesToSignUpWithAlreadyExistingEmail() {
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
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByEmail((String) any())).thenReturn(ofResult);
        doThrow(new UserAlreadyExists("Not all who wander are lost")).when(httpSession)
                .setAttribute((String) any(), (Object) any());
        UserLoginDto userLoginDto = mock(UserLoginDto.class);
        when(userLoginDto.getEmail()).thenReturn("jane.doe@example.org");
        when(userLoginDto.getPassword()).thenReturn("iloveyou");
        assertThrows(UserAlreadyExists.class, () -> userServiceImpl.login(userLoginDto));
        verify(userRepository).findByEmail((String) any());
        verify(httpSession).setAttribute((String) any(), (Object) any());
        verify(userLoginDto).getEmail();
        verify(userLoginDto).getPassword();
    }

    @Test
    void testLogin() {
        User user = mock(User.class);
        when(user.getId()).thenReturn(123L);
        when(user.getPassword()).thenReturn("iloveyou");
        doNothing().when(user).setComments((List<Comment>) any());
        doNothing().when(user).setEmail((String) any());
        doNothing().when(user).setFirstName((String) any());
        doNothing().when(user).setId((Long) any());
        doNothing().when(user).setJoined_at((LocalDateTime) any());
        doNothing().when(user).setLastName((String) any());
        doNothing().when(user).setLikedItems((List<LikedItems>) any());
        doNothing().when(user).setPassword((String) any());
        doNothing().when(user).setPosts((List<Post>) any());
        doNothing().when(user).setRole((Role) any());
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
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByEmail((String) any())).thenReturn(ofResult);
        doNothing().when(httpSession).setAttribute((String) any(), (Object) any());
        UserLoginDto userLoginDto = mock(UserLoginDto.class);
        when(userLoginDto.getEmail()).thenReturn("jane.doe@example.org");
        when(userLoginDto.getPassword()).thenReturn("iloveyou");
        assertEquals("Successfully Signed In", userServiceImpl.login(userLoginDto));
        verify(userRepository).findByEmail((String) any());
        verify(user).getId();
        verify(user).getPassword();
        verify(user).setComments((List<Comment>) any());
        verify(user).setEmail((String) any());
        verify(user).setFirstName((String) any());
        verify(user).setId((Long) any());
        verify(user).setJoined_at((LocalDateTime) any());
        verify(user).setLastName((String) any());
        verify(user).setLikedItems((List<LikedItems>) any());
        verify(user).setPassword((String) any());
        verify(user).setPosts((List<Post>) any());
        verify(user).setRole((Role) any());
        verify(httpSession, atLeast(1)).setAttribute((String) any(), (Object) any());
        verify(userLoginDto).getEmail();
        verify(userLoginDto).getPassword();
    }


    @Test
    void testLogout() {
        doNothing().when(httpSession).invalidate();
        assertEquals("User Logged Out", userServiceImpl.logout());
        verify(httpSession).invalidate();
    }
}

