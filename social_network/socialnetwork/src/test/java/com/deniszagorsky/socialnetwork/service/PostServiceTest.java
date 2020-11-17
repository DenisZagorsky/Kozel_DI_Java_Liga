package com.deniszagorsky.socialnetwork.service;

import com.deniszagorsky.socialnetwork.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static com.deniszagorsky.socialnetwork.prototype.PostPrototype.*;
import static com.deniszagorsky.socialnetwork.prototype.UserPrototype.user;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PostServiceTest {

    private PostService postService = new PostService();

    @BeforeEach
    void setUp() {
        postService.postRepository = mock(PostRepository.class);
        postService.userService = mock(UserService.class);
    }

    @Test
    void testCreate() {
        when(postService.userService.findOneById(user().getId())).thenReturn(user());
        when(postService.postRepository.save(any())).thenReturn(post());

        String strId = "03900221-665a-4727-b527-bd6ae4ae6038";

        UUID id = postService.create(user().getId(), postCreationDto());

        assertThat(id, notNullValue());
        assertThat(id, equalTo(UUID.fromString(strId)));
    }

    @Test
    void testUpdate() {
        when(postService.postRepository.findById(post().getId())).thenReturn(Optional.of(post()));
        when(postService.postRepository.save(any())).thenReturn(post());

        String strId = "03900221-665a-4727-b527-bd6ae4ae6038";

        UUID id = postService.update(post().getId(), postEditDto());

        assertThat(id, notNullValue());
        assertThat(id, equalTo(UUID.fromString(strId)));
    }

    @Test
    void testUpdateThrowsRuntimeException() {
        when(postService.postRepository.findById(post().getId())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> postService.update(post().getId(), postEditDto()),
                "Post has not been found");
    }

}