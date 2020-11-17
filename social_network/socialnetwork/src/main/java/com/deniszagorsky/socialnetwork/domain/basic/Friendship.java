package com.deniszagorsky.socialnetwork.domain.basic;

import com.deniszagorsky.socialnetwork.domain.embeddable.FriendshipKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Отношение "дружба" между пользователям
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "friendship")
public class Friendship {

    /**
     * Идентификатор
     */
    @EmbeddedId
    private FriendshipKey id;

    /**
     * Первый пользователь
     */
    @ManyToOne
    @MapsId("firstUserId")
    @JoinColumn(name = "first_user_id")
    private User firstUser;

    /**
     * Второй пользователь
     */
    @ManyToOne
    @MapsId("secondUserId")
    @JoinColumn(name = "second_user_id")
    private User secondUser;

    /**
     * Признак принятия предложения на добавление в друзья первым пользователям
     */
    @Column(name = "is_accepted_by_first_user", nullable = false)
    private Boolean isAcceptedByFirstUser = false;

    /**
     * Признак принятия предложения на добавление в друзья вторым пользователям
     */
    @Column(name = "is_accepted_by_second_user", nullable = false)
    private Boolean isAcceptedBySecondUser = false;

}
