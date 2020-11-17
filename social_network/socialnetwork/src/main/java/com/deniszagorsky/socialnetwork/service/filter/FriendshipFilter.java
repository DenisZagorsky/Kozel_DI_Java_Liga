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
 * Фильтр для поиска отношения "дружба" между двумя пользователями
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipFilter implements Filter {

    /**
     * Первый пользователь
     */
    private User firstUser;

    /**
     * Второй пользователь
     */
    private User secondUser;

    @Override
    public Specification<Friendship> toSpecification() {
        return where(ServiceSpecification.<Friendship>equal("firstUser", firstUser))
                .and(ServiceSpecification.equal("secondUser", secondUser))
                .or(where(ServiceSpecification.<Friendship>equal("firstUser", secondUser))
                .and(ServiceSpecification.equal("secondUser", firstUser)));
    }

}
