package com.deniszagorsky.socialnetwork.service;

import com.deniszagorsky.socialnetwork.domain.basic.Post;
import com.deniszagorsky.socialnetwork.domain.basic.User;
import com.deniszagorsky.socialnetwork.dto.PostCreationDto;
import com.deniszagorsky.socialnetwork.dto.PostEditDto;
import com.deniszagorsky.socialnetwork.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Сервис для работы с постами
 */

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserService userService;

    /**
     * Создание новой записи в БД
     * @param userId Идентификатор пользовател
     * @param dto Данные поста для создания
     * @return Идентификатор поста
     */
    @Transactional
    public UUID create(UUID userId, PostCreationDto dto) {
        User user = userService.findOneById(userId);

        Post post = convertPostCreationDtoToPost(dto);
        post.setUser(user);
        post = postRepository.save(post);

        return post.getId();
    }

    /**
     * Редактирование существующей записи в БД
     * @param postId Идентификатор поста
     * @param dto Данные поста для редактирования
     * @return Идентификатор поста
     */
    @Transactional
    public UUID update(UUID postId, PostEditDto dto) {
        Post entity = findOneById(postId);
        Post post = convertPostEditDtoToPost(dto, entity);
        post = postRepository.save(post);

        return post.getId();
    }

    /**
     * Удаление записи из БД
     * @param postId Идентификатор поста
     */
    @Transactional
    public void delete(UUID postId) {
        Post post = findOneById(postId);

        postRepository.delete(post);
    }

    /**
     * Получение поста по идентификатору
     * @param id Идентификатор поста
     * @return Сущность {@link Post}
     */
    private Post findOneById(UUID id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post has not been found"));
    }

    /**
     * Преобразование DTO-объекта {@link PostCreationDto} в сущность {@link Post}
     * @param dto Данные поста для создания
     * @return Сущность {@link Post}
     */
    private Post convertPostCreationDtoToPost(PostCreationDto dto) {
        Post entity = new Post();
        entity.setBody(dto.getBody());
        entity.setDatePublished(LocalDateTime.now());

        return entity;
    }

    /**
     * Преобразование DTO-объекта {@link PostEditDto} в сущность {@link Post}
     * @param dto Данные поста для редактирования
     * @param entity Сущность {@link Post}
     * @return Сущность {@link Post}
     */
    private Post convertPostEditDtoToPost(PostEditDto dto, Post entity) {
        entity.setBody(dto.getBody());

        return entity;
    }

}
