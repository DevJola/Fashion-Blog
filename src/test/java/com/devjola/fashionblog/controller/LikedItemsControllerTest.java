package com.devjola.fashionblog.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.devjola.fashionblog.service.LikedItemsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {LikedItemsController.class})
@ExtendWith(SpringExtension.class)
class LikedItemsControllerTest {
    @Autowired
    private LikedItemsController likedItemsController;

    @MockBean
    private LikedItemsService likedItemsService;


    @Test
    void testLikePost() throws Exception {
        doNothing().when(likedItemsService).likePost((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/likes/{id}", 123L);
        MockMvcBuilders.standaloneSetup(likedItemsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("post liked"));
    }



    @Test
    void testNoOfLikesPerPost() throws Exception {
        when(likedItemsService.noOfLikesPerPost((Long) any())).thenReturn(1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/likes/{id}", 123L);
        MockMvcBuilders.standaloneSetup(likedItemsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("1"));
    }
}

