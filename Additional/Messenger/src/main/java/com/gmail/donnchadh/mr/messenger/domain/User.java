package com.gmail.donnchadh.mr.messenger.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    /**
     * Идентификатор пользователя
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    /**
     * Адрес электронной почты пользователя
     * Добавил данное поле, поскольку оно является уникальным для каждого пользователя и
     * работа с ним проще, нежели чем с атрибутом id
     */
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    /**
     * Фамилия пользователя
     */
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /**
     * Имя пользователя
     */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /**
     * Отчество пользователя
     */
    @Column(name = "patronymic")
    private String patronymic;

    /**
     * Дата рождения пользователя
     */
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    /**
     * Информация о пользователе
     */
    @Column(name = "personal_inf")
    private String personalInf;

    /**
     * Роль пользователя в диалоге
     */
    @Transient
    private Role role;

    public User() {}

    public User(String email, String lastName, String firstName,
                String patronymic, int year, int month, int dayOfMonth) {
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        setDateOfBirth(year, month, dayOfMonth);
    }

    public UUID getId() { return id; }

    public String getEmail() { return email; }

    public void setEmail() { this.email = email; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getPatronymic() {
        if (patronymic == null) { return ""; }
        else { return patronymic; }
    }

    public void setPatronymic(String patronymic) { this.patronymic = patronymic; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }

    public void setDateOfBirth(int year, int month, int dayOfMonth) {
        dateOfBirth = LocalDate.of(year, month, dayOfMonth);
    }

    public String getPersonalInf() { return personalInf; }

    public void setPersonalInf(String personalInf) { this.personalInf = personalInf; }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        String fi = lastName + " " + firstName + " ";
        return (patronymic == null) ? fi.trim() : fi + patronymic;
    }

}
