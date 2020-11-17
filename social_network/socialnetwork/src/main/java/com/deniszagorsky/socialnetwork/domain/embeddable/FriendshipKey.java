package com.deniszagorsky.socialnetwork.domain.embeddable;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

/**
 * Составной ключ для сущности {@link com.deniszagorsky.socialnetwork.domain.basic.Friendship}
 */

@Embeddable
@Data
public class FriendshipKey implements Serializable {

    /**
     * Идентификатор первого пользователя
     */
    @Column(name = "first_user_id")
    private UUID firstUserId;

    /**
     * Идентификатор второго пользователя
     */
    @Column(name = "second_user_id")
    private UUID secondUserId;

}
