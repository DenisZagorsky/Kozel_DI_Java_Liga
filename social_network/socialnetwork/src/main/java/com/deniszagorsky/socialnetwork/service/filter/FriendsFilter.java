package com.deniszagorsky.socialnetwork.service.filter;

import com.deniszagorsky.socialnetwork.domain.basic.Friendship;
import com.deniszagorsky.socialnetwork.domain.basic.User;
import com.deniszagorsky.socialnetwork.service.specification.ServiceSpecification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import static org.springframework.data.jpa.domain.Specification.where;

/**
 * Фильтр для поиска друзей пользователя
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendsFilter implements Filter {

    /**
     * Пользователь
     */
    private User user;

    @Override
    public Specification<Friendship> toSpecification() {
        return where(ServiceSpecification.<Friendship>equal("firstUser", user))
                .and(ServiceSpecification.equal("isAcceptedByFirstUser", true))
                .and(ServiceSpecification.equal("isAcceptedBySecondUser", true))
                .or(where(ServiceSpecification.<Friendship>equal("secondUser", user))
                .and(ServiceSpecification.equal("isAcceptedByFirstUser", true))
                .and(ServiceSpecification.equal("isAcceptedBySecondUser", true)));
    }

}
