package com.deniszagorsky.socialnetwork.service;

import com.deniszagorsky.socialnetwork.domain.basic.User;
import com.deniszagorsky.socialnetwork.dto.UserByListDto;
import com.deniszagorsky.socialnetwork.dto.UserEditDto;
import com.deniszagorsky.socialnetwork.dto.UserPageDto;
import com.deniszagorsky.socialnetwork.dto.UserRegistrationDto;
import com.deniszagorsky.socialnetwork.exception.EmailNotValidException;
import com.deniszagorsky.socialnetwork.repository.FriendshipRepository;
import com.deniszagorsky.socialnetwork.repository.UserRepository;
import com.deniszagorsky.socialnetwork.service.filter.UserSearchFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

import static com.deniszagorsky.socialnetwork.prototype.UserPrototype.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private UserService userService = new UserService();

    @BeforeEach
    void setUp() {
        userService.userRepository = mock(UserRepository.class);
        userService.friendshipRepository = mock(FriendshipRepository.class);
    }

    @Test
    void testCreate() {
        when(userService.userRepository.save(userWithoutId())).thenReturn(user());

        String strId = "68fdb339-26ea-4218-8d28-b7171cad3a31";

        UUID id = userService.create(userRegistrationDto());

        assertThat(id, notNullValue());
        assertThat(id, equalTo(UUID.fromString(strId)));
    }

    @Test
    void testCreateThrowsEmailNotValidException() {
        UserRegistrationDto dto = userRegistrationDto();
        dto.setEmail("Invalid");

        assertThrows(EmailNotValidException.class,
                () -> userService.create(dto));
    }

    @Test
    void testCreateThrowsRuntimeException() {
        when(userService.userRepository.findOne(any(Specification.class))).thenReturn(Optional.of(user()));

        assertThrows(RuntimeException.class,
                () -> userService.create(userRegistrationDto()),
                "This email address is already being used");
    }

    @Test
    void testUpdate() {
        User user = user();
        user.setInstagram("zagorsky.denis");
        user.setWebsite("deniszagorsky.com");

        when(userService.userRepository.findById(user().getId())).thenReturn(Optional.of(user()));
        when(userService.userRepository.save(any())).thenReturn(user);

        String strId = "68fdb339-26ea-4218-8d28-b7171cad3a31";

        UUID id = userService.update(user().getId(), userEditDto());

        assertThat(id, notNullValue());
        assertThat(id, equalTo(UUID.fromString(strId)));
    }

    @Test
    void testUpdateThrowsRuntimeExceptionUserNotFound() {
        when(userService.userRepository.findById(user().getId())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> userService.update(user().getId(), userEditDto()),
                "User has not been found");
    }

    @Test
    void testUpdateThrowsRuntimeExceptionEmailAlreadyUsed() {
        when(userService.userRepository.findById(user().getId())).thenReturn(Optional.of(user()));
        when(userService.userRepository.findOne(any(Specification.class))).thenReturn(Optional.of(user()));

        UserEditDto userEditDto = userEditDto();
        userEditDto.setEmail("deniszagorsky@yandex.ru");

        assertThrows(RuntimeException.class,
                () -> userService.update(user().getId(), userEditDto()),
                "This email address is already being used");
    }

    @Test
    void testFindOne() {
        when(userService.userRepository.findById(user().getId())).thenReturn(Optional.of(user()));

        UserPageDto userPageDto = userService.findOne(user().getId());

        assertThat(userPageDto, notNullValue());
        assertThat(userPageDto.getFirstName(), equalTo(user().getFirstName()));
        assertThat(userPageDto.getLastName(), equalTo(user().getLastName()));
    }

    @Test
    void testFindOneThrowsRuntimeException() {
        when(userService.userRepository.findById(user().getId())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> userService.findOne(user().getId()),
                "User has not been found");
    }

    @Test
    void testFindAll() {
        UserSearchFilter filter = new UserSearchFilter("Denis", "Zagorsky", "Saint-Petersburg",
                4, 2, 1998);
        Pageable pageable = PageRequest.of(0, 10);

        when(userService.userRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(Page.empty());

        Page<UserByListDto> pageOfUsers =  userService.findAll(filter, pageable);

        assertThat(pageOfUsers, notNullValue());
        assertThat(pageOfUsers, is(Page.empty()));
    }

}