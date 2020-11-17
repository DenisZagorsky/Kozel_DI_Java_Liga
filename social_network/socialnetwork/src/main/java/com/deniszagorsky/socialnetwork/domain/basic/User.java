package com.deniszagorsky.socialnetwork.domain.basic;

import com.deniszagorsky.socialnetwork.domain.embeddable.Email;
import com.deniszagorsky.socialnetwork.domain.embeddable.Role;
import com.deniszagorsky.socialnetwork.domain.embeddable.Sex;
import com.deniszagorsky.socialnetwork.domain.superclass.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Пользователь
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends Identifiable {

    /**
     * Адрес электронной почты
     */
    @Embedded
    private Email email;

    /**
     * Пароль
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Фамилия
     */
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /**
     * Имя
     */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /**
     * Дата рождения
     */
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    /**
     * Пол
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "sex", nullable = false)
    private Sex sex;

    /**
     * Город
     */
    @Column(name = "city", nullable = false)
    private String city;

    /**
     * Интересы
     */
    @Column(name = "interests")
    private String interests;

    /**
     * Аккаунт Instagram
     */
    @Column(name = "instagram")
    private String instagram;

    /**
     * URL вебсайта
     */
    @Column(name = "website")
    private String website;

    /**
     * Список постов, опубликованных пользователем
     */
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @OrderBy("datePublished DESC")
    private List<Post> posts = new ArrayList<>();

    /**
     * Роль
     */
    @Transient
    private Role role;

    @Override
    public String toString() { return firstName + " " + lastName; }

}
