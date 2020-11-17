package com.deniszagorsky.socialnetwork.controller;

import com.deniszagorsky.socialnetwork.dto.UserByListDto;
import com.deniszagorsky.socialnetwork.dto.UserIdDto;
import com.deniszagorsky.socialnetwork.service.FriendshipService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

/**
 * Контроллер для работы с отношением "дружба" между пользователями
 */

@RestController
@RequestMapping("/socialnetwork")
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipController {

    @Autowired
    FriendshipService friendshipService;

    /**
     * Отправка запроса на добавление в друзья
     * @param userDto Данные, необходимые для идентификации пользователя, который отправил запрос
     * @param friendId Идентификатор пользователя, которому отправлен запрос
     */
    @PostMapping("{friendId}")
    public void sendFriendshipRequest(@RequestBody UserIdDto userDto, @PathVariable UUID friendId) {
        friendshipService.sendFriendshipRequest(userDto.getId(), friendId);
    }

    /**
     * Принятие запроса на добавление в друзья
     * @param friendId Идентификатор пользователя, который отправил запрос
     * @param userDto Данные, необходимые для идентификации пользователя, которому отправлен запрос
     */
    @PutMapping("{friendId}")
    public void acceptFriendshipRequest(@PathVariable UUID friendId, @RequestBody UserIdDto userDto) {
        friendshipService.acceptFriendshipRequest(friendId, userDto.getId());
    }

    /**
     * Удаление пользователя из списка друзей или отписка от его обновлений
     * @param userDto  Данные, необходимые для идентификации пользователя, который отправил запрос на удаление из друзей
     * @param friendId Идентификатор пользователя, которого необходимо удалить из друзей
     */
    @PutMapping("{friendId}/remove_friendship")
    public void removeFriendship(@RequestBody UserIdDto userDto, @PathVariable UUID friendId) {
        friendshipService.removeOrUpdate(userDto.getId(), friendId);
    }

    /**
     * Получение списка друзей
     * @param userId Идентификатор пользователя, для которого необходимо получить список друзей
     * @param pageParameters Параметры настройки пагинации
     * @return Страница с пользователями
     */
    @GetMapping("{userId}/friends")
    public Page<UserByListDto> getAllFriends(@PathVariable UUID userId,
                                             @RequestParam Map<String, String> pageParameters) {
        Pageable pageable = PageRequest.of(
                Integer.parseInt(pageParameters.get("p")),
                Integer.parseInt(pageParameters.get("s"))
        );

        return friendshipService.findAllFriends(userId, pageable);
    }

}
