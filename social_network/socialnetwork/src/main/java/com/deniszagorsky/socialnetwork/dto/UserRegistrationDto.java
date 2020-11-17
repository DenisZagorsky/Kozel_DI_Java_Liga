package com.deniszagorsky.socialnetwork.dto;

import com.deniszagorsky.socialnetwork.domain.embeddable.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

/**
 * DTO данных пользователя для регистрации
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {

    /**
     * Адрес электронной почты
     */
    @NotEmpty
    @Length(max = 128)
    private String email;

    /**
     * Пароль
     */
    @NotEmpty
    @Length(min = 8, max = 32)
    private String password;

    /**
     * Фамилия
     */
    @NotEmpty
    @Length(max = 128)
    private String lastName;

    /**
     * Имя
     */
    @NotEmpty
    @Length(max = 128)
    private String firstName;

    /**
     * День рождения
     */
    @Positive
    @Max(value = 31)
    private Integer dayOfBirth;

    /**
     * Месяц рождения
     */
    @Positive
    @Max(value = 12)
    private Integer monthOfBirth;

    /**
     * Год рождения
     */
    @Positive
    private Integer yearOfBirth;

    /**
     * Пол
     */
    private Sex sex;

    /**
     * Город
     */
    @NotEmpty
    @Length(max = 128)
    private String city;

    /**
     * Интересы
     */
    @Length(max = 250)
    private String interests;

}
