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
 * Фильтр для поиска сущности {@link Friendship} по пользователю
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipSearchFilter implements Filter {

    /**
     * Пользователь
     */
    private User user;

    @Override
    public Specification<Friendship> toSpecification() {
        return where(ServiceSpecification.<Friendship>equal("firstUser", user))
                .or(ServiceSpecification.equal("secondUser", user));
    }

}
