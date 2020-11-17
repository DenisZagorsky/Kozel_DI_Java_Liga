package com.deniszagorsky.socialnetwork.service.filter;

import com.deniszagorsky.socialnetwork.domain.basic.User;
import com.deniszagorsky.socialnetwork.domain.embeddable.Email;
import com.deniszagorsky.socialnetwork.service.specification.ServiceSpecification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import static org.springframework.data.jpa.domain.Specification.where;

/**
 * Фильтр для проверки на наличие пользователя с указанным адресом электронной почты
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserByEmailFilter implements Filter {

    /**
     * Адрес электронной почты
     */
    private Email email;

    @Override
    public Specification<User> toSpecification() {
        return where(ServiceSpecification.equal("email", email));
    }

}
