package com.deniszagorsky.socialnetwork.domain.basic;

import com.deniszagorsky.socialnetwork.domain.superclass.Identifiable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Пост, опубликонный пользователем на персональной странице
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post")
public class Post extends Identifiable {

    /**
     * Пользователь, опубликовавший пост
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    /**
     * Тело поста
     */
    @Column(name = "body", nullable = false)
    private String body;

    /**
     * Дата и время публикации поста
     */
    @Column(name = "date_published", nullable = false)
    private LocalDateTime datePublished;

    @Override
    public String toString() {
        String post = "[%s] %s: %s";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return String.format(post, datePublished.format(formatter), user, body);
    }

}
