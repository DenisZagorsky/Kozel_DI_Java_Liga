package com.deniszagorsky.socialnetwork.service;

import com.deniszagorsky.socialnetwork.dto.UserByListDto;
import com.deniszagorsky.socialnetwork.repository.FriendshipRepository;
import com.deniszagorsky.socialnetwork.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import static com.deniszagorsky.socialnetwork.prototype.UserPrototype.user;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FriendshipServiceTest {

    private FriendshipService friendshipService = new FriendshipService();

    @BeforeEach
    void setUp() {
        friendshipService.userRepository = mock(UserRepository.class);
        friendshipService.friendshipRepository = mock(FriendshipRepository.class);
        friendshipService.userService = mock(UserService.class);
    }

    @Test
    void findAllFriends() {
        when(friendshipService.userService.findOneById(user().getId())).thenReturn(user());

        Pageable pageable = PageRequest.of(0, 10);

        when(friendshipService.friendshipRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(Page.empty());

        Page<UserByListDto> pageOfUsers =  friendshipService.findAllFriends(user().getId(), pageable);

        assertThat(pageOfUsers, notNullValue());
        assertThat(pageOfUsers, is(Page.empty()));
    }

}