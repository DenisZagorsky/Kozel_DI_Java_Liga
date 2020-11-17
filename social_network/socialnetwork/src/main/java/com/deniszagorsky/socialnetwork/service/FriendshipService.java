package com.deniszagorsky.socialnetwork.service;

import com.deniszagorsky.socialnetwork.domain.basic.Friendship;
import com.deniszagorsky.socialnetwork.domain.basic.User;
import com.deniszagorsky.socialnetwork.domain.embeddable.FriendshipKey;
import com.deniszagorsky.socialnetwork.domain.embeddable.Role;
import com.deniszagorsky.socialnetwork.dto.UserByListDto;
import com.deniszagorsky.socialnetwork.repository.FriendshipRepository;
import com.deniszagorsky.socialnetwork.repository.UserRepository;
import com.deniszagorsky.socialnetwork.service.filter.FriendsFilter;
import com.deniszagorsky.socialnetwork.service.filter.FriendshipFilter;
import com.deniszagorsky.socialnetwork.service.filter.FriendshipRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Сервис для работы с отношением "дружба" между пользователями
 */

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FriendshipService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FriendshipRepository friendshipRepository;

    @Autowired
    UserService userService;

    /**
     * Создание отношения "дружба" между пользователя и добавление новой записи в БД
     * @param userId Идентификатор пользователя, который отправил запрос на добавление в друзья
     * @param friendId Идентификатор пользователя, которому отправлен запрос на добавление в друзья
     */
    @Transactional
    public void sendFriendshipRequest(UUID userId, UUID friendId) {
        User user = userService.findOneById(userId);
        User friend = userService.findOneById(friendId);

        FriendshipKey friendshipKey = new FriendshipKey();
        friendshipKey.setFirstUserId(user.getId());
        friendshipKey.setSecondUserId(friend.getId());

        Friendship friendship = new Friendship();
        friendship.setId(friendshipKey);
        friendship.setFirstUser(user);
        friendship.setSecondUser(friend);
        friendship.setIsAcceptedByFirstUser(true);

        friendshipRepository.save(friendship);
    }

    /**
     * Принятие запроса на добавление в друзья и редактирование существующей записи в БД
     * @param friendId Идентификатор пользователя, который отправил запрос на добавление в друзья
     * @param userId Идентификатор пользователя, которому отправлен запрос на добавление в друзья
     */
    @Transactional
    public void acceptFriendshipRequest(UUID friendId, UUID userId) {
        User user = userService.findOneById(userId);
        User friend = userService.findOneById(friendId);

        FriendshipRequestFilter filter = new FriendshipRequestFilter(friend, user);
        Friendship friendship = friendshipRepository.findOne(filter.toSpecification())
                .orElseThrow(() -> new RuntimeException("Request has not been found"));
        friendship.setIsAcceptedBySecondUser(true);

        friendshipRepository.save(friendship);
    }

    /**
     * Удаление пользователя из списка друзей или отписка от его обновлений
     * и удаление / редактирование существующей записи в БД
     * @param userId Идентификатор пользователя, который отправил запрос на удаление из друзей
     * @param friendId Идентификатор пользователя, которого необходимо удалить из друзей
     */
    @Transactional
    public void removeOrUpdate(UUID userId, UUID friendId) {
        User user = userService.findOneById(userId);
        User friend = userService.findOneById(friendId);

        FriendshipFilter filter = new FriendshipFilter(user, friend);
        Friendship friendship = friendshipRepository.findOne(filter.toSpecification())
                .orElseThrow(() -> new RuntimeException("Friend has not been found"));
        if (friendship.getFirstUser().getId().equals(userId)) {
            friendship.setIsAcceptedByFirstUser(false);
        } else {
            friendship.setIsAcceptedBySecondUser(false);
        }

        if (friendship.getIsAcceptedByFirstUser().equals(false)
                && friendship.getIsAcceptedBySecondUser().equals(false)) {
            friendshipRepository.delete(friendship);
        } else {
            friendshipRepository.save(friendship);
        }
    }

    /**
     * Получение списка друзей
     * @param userId Идентификатор пользователя, для которого необходимо получить список друзей
     * @param pageable Параметры настройки пагинации
     * @return Страница с пользователями
     */
    public Page<UserByListDto> findAllFriends(UUID userId, Pageable pageable) {
        User user = userService.findOneById(userId);

        FriendsFilter filter = new FriendsFilter(user);

        return friendshipRepository.findAll(filter.toSpecification(), pageable)
                .map(friendship -> {
                    if (friendship.getId().getFirstUserId().equals(userId)) {
                        friendship.getFirstUser().setRole(Role.USER);
                        friendship.getSecondUser().setRole(Role.FRIEND);
                    } else {
                        friendship.getFirstUser().setRole(Role.FRIEND);
                        friendship.getSecondUser().setRole(Role.USER);
                    }

                    return friendship;
                })
                .map(this::convertFriendshipToUserByListDto);
    }

    /**
     * Преобразование сущности {@link Friendship} в DTO-объект {@link UserByListDto}
     * @param friendship Сущность {@link Friendship}
     * @return Данные пользователя для реестра пользователей
     */
    private UserByListDto convertFriendshipToUserByListDto(Friendship friendship) {
        UserByListDto dto = new UserByListDto();
        User entity;
        if (friendship.getFirstUser().getRole().equals(Role.FRIEND)) {
            entity = userRepository.findById(friendship.getId().getFirstUserId())
                    .get();
        } else {
            entity = userRepository.findById(friendship.getId().getSecondUserId())
                    .get();
        }

        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setCity(entity.getCity());
        dto.setDateOfBirth(entity.getDateOfBirth());

        return dto;
    }

}
