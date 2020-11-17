package com.deniszagorsky.socialnetwork.dto;

import com.deniszagorsky.socialnetwork.domain.basic.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO данных пользователя для персональной страницы
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPageDto {

    /**
     * Имя
     */
    private String firstName;

    /**
     * Фамилия
     */
    private String lastName;

    /**
     * Город
     */
    private String city;

    /**
     * Возраст
     */
    private Integer age;

    /**
     * Аккаунт Instagram
     */
    private String instagram;

    /**
     * URL вебсайта
     */
    private String website;

    /**
     * Список постов, опубликованных на персональной странице
     */
    private List<Post> posts = new ArrayList<>();

    public void setAge(LocalDate dateOfBirth) {
        age = (int) ChronoUnit.YEARS.between(dateOfBirth, LocalDate.now());
    }

}
