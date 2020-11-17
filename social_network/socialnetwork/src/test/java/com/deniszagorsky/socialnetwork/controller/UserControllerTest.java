package com.deniszagorsky.socialnetwork.controller;

import com.deniszagorsky.socialnetwork.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.deniszagorsky.socialnetwork.prototype.UserPrototype.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService)).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreateUser() throws Exception {
        when(userService.create(any())).thenReturn(user().getId());
        mockMvc.perform(post("/socialnetwork/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRegistrationDto())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(user().getId())));
    }

    @Test
    void testUpdateUser() throws Exception {
        when(userService.update(any(), any())).thenReturn(user().getId());
        mockMvc.perform(put("/socialnetwork/68fdb339-26ea-4218-8d28-b7171cad3a31/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userEditDto())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(user().getId())));
    }

    @Test
    void testRemoveUser() {
//        when(userService.remove(any())).thenReturn(void);
    }

    @Test
    void testGetUser() throws Exception {
        when(userService.findOne(any())).thenReturn(userPageDto());
        mockMvc.perform(get("/socialnetwork/68fdb339-26ea-4218-8d28-b7171cad3a31")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(userPageDto())));
    }

    @Test
    void testGetAllUsers() {
//        when(userService.findAll(any(), any())).thenReturn()
    }
}