package com.devjola.fashionblog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.devjola.fashionblog.dto.UserLoginDto;
import com.devjola.fashionblog.dto.UserSignUpDto;
import com.devjola.fashionblog.enums.Role;
import com.devjola.fashionblog.model.Comment;
import com.devjola.fashionblog.model.LikedItems;
import com.devjola.fashionblog.model.Post;
import com.devjola.fashionblog.model.User;
import com.devjola.fashionblog.repository.UserRepository;
import com.devjola.fashionblog.service.UserService;
import com.devjola.fashionblog.service.serviceImpl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AuthenticationController.class})
@ExtendWith(SpringExtension.class)
class AuthenticationControllerTest {
    @Autowired
    private AuthenticationController authenticationController;

    @MockBean
    private UserService userService;


    @Test
    void testSignUp() throws Exception {
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
        when(userService.signUp((UserSignUpDto) any())).thenReturn(user);

        UserSignUpDto userSignUpDto = new UserSignUpDto();
        userSignUpDto.setEmail("jane.doe@example.org");
        userSignUpDto.setFirstName("Jane");
        userSignUpDto.setLastName("Doe");
        userSignUpDto.setPassword("iloveyou");
        userSignUpDto.setRole(Role.ADMIN);
        String content = (new ObjectMapper()).writeValueAsString(userSignUpDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(authenticationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"email\":\"jane.doe@example.org\",\"password\":\"iloveyou\",\"firstName\":\"Jane\",\"lastName\":\"Doe\","
                                        + "\"joined_at\":[1,1,1,1,1],\"role\":\"ADMIN\",\"posts\":[],\"comments\":[],\"likedItems\":[]}"));
    }


    @Test
    void testLogin() {
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
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByEmail((String) any())).thenReturn(Optional.of(user));
        AuthenticationController authenticationController = new AuthenticationController(
                new UserServiceImpl(userRepository, new MockHttpSession()));
        UserLoginDto userLoginDto = mock(UserLoginDto.class);
        when(userLoginDto.getEmail()).thenReturn("jane.doe@example.org");
        when(userLoginDto.getPassword()).thenReturn("iloveyou");
        ResponseEntity<String> actualLoginResult = authenticationController.login(userLoginDto);
        assertEquals("Successfully Signed In", actualLoginResult.getBody());
        assertEquals(HttpStatus.OK, actualLoginResult.getStatusCode());
        assertTrue(actualLoginResult.getHeaders().isEmpty());
        verify(userRepository).findByEmail((String) any());
        verify(userLoginDto).getEmail();
        verify(userLoginDto).getPassword();
    }


    @Test
    void testLogout() throws Exception {
        when(userService.logout()).thenReturn("Logout");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/auth/log-out");
        MockMvcBuilders.standaloneSetup(authenticationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Logout"));
    }
}

