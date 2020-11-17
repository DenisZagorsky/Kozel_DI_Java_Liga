package com.deniszagorsky.socialnetwork.service.filter;

import com.deniszagorsky.socialnetwork.domain.basic.User;
import com.deniszagorsky.socialnetwork.service.specification.ServiceSpecification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

import static org.springframework.data.jpa.domain.Specification.where;

/**
 * Фильтр для поиска пользователей
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchFilter implements Filter {

    /**
     * Имя
     */
    private String firstNameLike;

    /**
     * Фамилия
     */
    private String lastNameLike;

    /**
     * Город
     */
    private String city;

    /**
     * День рождения
     */
    private Integer dayOfBirth;

    /**
     * Месяц рождения
     */
    private Integer monthOfBirth;

    /**
     * Год рождения
     */
    private Integer yearOfBirth;

    @Override
    public Specification<User> toSpecification() {
        return where(ServiceSpecification.<User>like("firstName", firstNameLike))
                .and(ServiceSpecification.like("lastName", lastNameLike))
                .and(ServiceSpecification.alike("city", city))
                .and(ServiceSpecification.equal("dateOfBirth", LocalDate.of(yearOfBirth, monthOfBirth, dayOfBirth)));
    }

}
