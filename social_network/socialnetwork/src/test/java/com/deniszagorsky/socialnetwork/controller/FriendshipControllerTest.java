package com.deniszagorsky.socialnetwork.controller;

import com.deniszagorsky.socialnetwork.service.FriendshipService;
import com.deniszagorsky.socialnetwork.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FriendshipControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private FriendshipService friendshipService;

    @BeforeEach
    void setUp() {
        friendshipService = mock(FriendshipService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new FriendshipController(friendshipService)).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testSendFriendshipRequest() {

    }

    @Test
    void acceptFriendshipRequest() {

    }

    @Test
    void removeFriendship() {
    }

    @Test
    void getAllFriends() {

    }

}