package com.deniszagorsky.socialnetwork.controller;

import com.deniszagorsky.socialnetwork.dto.UserByListDto;
import com.deniszagorsky.socialnetwork.dto.UserEditDto;
import com.deniszagorsky.socialnetwork.dto.UserPageDto;
import com.deniszagorsky.socialnetwork.dto.UserRegistrationDto;
import com.deniszagorsky.socialnetwork.service.UserService;
import com.deniszagorsky.socialnetwork.service.filter.UserSearchFilter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

/**
 * Контроллер для работы с пользователем
 */

@RestController
@RequestMapping("/socialnetwork")
@NoArgsConstructor
@AllArgsConstructor
public class UserController {

    @Autowired
    UserService userService;

    /**
     * Создание нового пользователя
     * @param userDto Данные пользователя для регистрации
     * @return Идентификатор пользователя
     */
    @PostMapping("/join")
    public UUID createUser(@RequestBody @Valid UserRegistrationDto userDto) {
        return userService.create(userDto);
    }

    /**
     * Редактирование учетной записи пользователя
     * @param userId Идентификатор пользователя
     * @param userDto Данные пользователя для редактирования учетной записи
     * @return Идентификатор пользователя
     */
    @PutMapping("/{userId}/edit")
    public UUID updateUser(@PathVariable UUID userId, @RequestBody @Valid UserEditDto userDto) { return userService.update(userId, userDto); }

    /**
     * Удаление учетной записи
     * @param userId Идентификатор пользователя
     */
    @DeleteMapping("/{userId}/delete")
    public void removeUser(@PathVariable UUID userId) {
        userService.remove(userId);
    }

    /**
     * Получение страницы пользователя
     * @param userId Идентификатор пользователя
     * @return Данные пользователя для персональной страницы
     */
    @GetMapping("/{userId}")
    public UserPageDto getUser(@PathVariable UUID userId) { return userService.findOne(userId); }

    /**
     * Получение всех пользователей, удовлетворяющих критериям поиска
     * @param parametersOfSearch Критерии поиска
     * @return Страница с пользователями
     */
    @GetMapping("/search")
    public Page<UserByListDto> getAllUsers(@RequestParam Map<String, String> parametersOfSearch) {
        UserSearchFilter userSearchFilter = new UserSearchFilter(
                parametersOfSearch.get("fnl"),
                parametersOfSearch.get("lnl"),
                parametersOfSearch.get("c"),
                Integer.parseInt(parametersOfSearch.get("dob")),
                Integer.parseInt(parametersOfSearch.get("mob")),
                Integer.parseInt(parametersOfSearch.get("yob"))
        );
        Pageable pageable = PageRequest.of(
                Integer.parseInt(parametersOfSearch.get("p")),
                Integer.parseInt(parametersOfSearch.get("s"))
        );

        return userService.findAll(userSearchFilter, pageable);
    }

}
