package com.deniszagorsky.socialnetwork.controller;

import com.deniszagorsky.socialnetwork.dto.PostCreationDto;
import com.deniszagorsky.socialnetwork.dto.PostEditDto;
import com.deniszagorsky.socialnetwork.service.PostService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

/**
 * Контроллер для работы с постами
 */

@RestController
@RequestMapping("/socialnetwork")
@NoArgsConstructor
@AllArgsConstructor
public class PostController {

    @Autowired
    PostService postService;

    /**
     * Публикация поста на персональной странице
     * @param userId Идентификатор пользователя
     * @param postDto Данные поста для создания
     * @return Идентификатор поста
     */
//    /socialnetwork/1/create_post
//    /api/v1/socialnetwork/posts
    @PostMapping("/{userId}/create_post")
    public UUID createPost(@PathVariable UUID userId, @RequestBody @Valid PostCreationDto postDto) {
        return postService.create(userId, postDto);
    }

    /**
     * Редактирование поста
     * @param postId Идентификатор поста
     * @param postDto Данные поста для редактирования
     * @return Идентификатор поста
     */
    @PutMapping("/{userId}/post/{postId}/edit")
    public UUID editPost(@PathVariable UUID postId, @RequestBody @Valid PostEditDto postDto) {
        return postService.update(postId, postDto);
    }

    /**
     * Удаление поста
     * @param postId Идентификатор поста
     */
    @DeleteMapping("/{userId}/post/{postId}/delete")
    public void deletePost(@PathVariable UUID postId) { postService.delete(postId); }

}
