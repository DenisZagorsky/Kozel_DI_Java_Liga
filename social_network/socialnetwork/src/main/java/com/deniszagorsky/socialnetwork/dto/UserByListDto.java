package com.deniszagorsky.socialnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO данных пользователя для реестра пользователей
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserByListDto {

    /**
     * Идентификатор
     */
    private UUID id;

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
     * Дата рождения
     */
    private LocalDate dateOfBirth;

}
