package com.deniszagorsky.socialnetwork.controller;

import com.deniszagorsky.socialnetwork.service.PostService;
import com.deniszagorsky.socialnetwork.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.deniszagorsky.socialnetwork.prototype.PostPrototype.post;
import static com.deniszagorsky.socialnetwork.prototype.PostPrototype.postEditDto;
import static com.deniszagorsky.socialnetwork.prototype.PostPrototype.postCreationDto;
import static com.deniszagorsky.socialnetwork.prototype.UserPrototype.user;
import static com.deniszagorsky.socialnetwork.prototype.UserPrototype.userRegistrationDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private PostService postService;

    @BeforeEach
    void setUp() {
        postService = mock(PostService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new PostController(postService)).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreatePost() throws Exception {
        when(postService.create(any(), any())).thenReturn(post().getId());
        mockMvc.perform(post("/socialnetwork/68fdb339-26ea-4218-8d28-b7171cad3a31/create_post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postCreationDto())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(post().getId())));
    }

    @Test
    void testEditPost() throws Exception {
        when(postService.update(any(), any())).thenReturn(post().getId());
        mockMvc.perform(put("/socialnetwork/68fdb339-26ea-4218-8d28-b7171cad3a31/post/03900221-665a-4727-b527-bd6ae4ae6038/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postEditDto())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(post().getId())));
    }

    @Test
    void deletePost() {
    }
}