package com.deniszagorsky.socialnetwork.service;

import com.deniszagorsky.socialnetwork.domain.basic.User;
import com.deniszagorsky.socialnetwork.domain.embeddable.Email;
import com.deniszagorsky.socialnetwork.dto.UserByListDto;
import com.deniszagorsky.socialnetwork.dto.UserEditDto;
import com.deniszagorsky.socialnetwork.dto.UserPageDto;
import com.deniszagorsky.socialnetwork.dto.UserRegistrationDto;
import com.deniszagorsky.socialnetwork.repository.FriendshipRepository;
import com.deniszagorsky.socialnetwork.repository.UserRepository;
import com.deniszagorsky.socialnetwork.service.filter.FriendshipSearchFilter;
import com.deniszagorsky.socialnetwork.service.filter.UserByEmailFilter;
import com.deniszagorsky.socialnetwork.service.filter.UserSearchFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

/**
 * Сервис для работы с пользователями
 */

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FriendshipRepository friendshipRepository;

    /**
     * Проверка данных пользователя и создание новой записи в БД
     * @param dto Данные поьзователя для регистрации
     * @return Идентификатор пользователя
     */
    @Transactional
    public UUID create(UserRegistrationDto dto) {
        User user = convertUserRegistrationDtoToUser(dto);
        try {
            userRepository.findOne(new UserByEmailFilter(new Email(dto.getEmail())).toSpecification())
                    .get();
            throw new RuntimeException("This email address is already being used");
        } catch(NoSuchElementException e) { }
        user = userRepository.save(user);

        return user.getId();
    }

    /**
     * Проверка данных пользователя и редактирование существующей записи в БД
     * @param id Идентификатор пользователя
     * @param dto Данные пользователя для редактирования учетной записи
     * @return Идентификатор пользователя
     */
    @Transactional
    public UUID update(UUID id, UserEditDto dto) {
        User user = findOneById(id);
        if (!dto.getEmail().equals(user.getEmail().getEmail())) {
            try {
                userRepository.findOne(new UserByEmailFilter(new Email(dto.getEmail())).toSpecification())
                        .get();
                throw new RuntimeException("This email address is already being used");
            } catch (NoSuchElementException e) { }
        }
        user = convertUserEditDtoToUser(dto, user);
        user = userRepository.save(user);

        return user.getId();
    }

    /**
     * Удаление записи из БД
     * @param id Идентификатор пользователя
     */
    @Transactional
    public void remove(UUID id) {
        User user = findOneById(id);
        FriendshipSearchFilter filter = new FriendshipSearchFilter(user);
        friendshipRepository.findAll(filter.toSpecification())
                .forEach(friendshipRepository::delete);

        userRepository.delete(user);
    }

    /**
     * Получение данных пользователя для персональной страницы по его идентификатору
     * @param id Идентификатор пользователя
     * @return Данные пользователя для персональной страницы
     */
    public UserPageDto findOne(UUID id) {
        return userRepository.findById(id)
                .map(this::convertUserToUserPageDto)
                .orElseThrow(() -> new RuntimeException("User has not been found"));
    }

    /**
     * Получение пользователя по идентификатору
     * @param id Идентификатор пользователя
     * @return Сущность {@link User}
     */
    protected User findOneById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User has not been found"));
    }

    /**
     * Получение всех пользователей, удовлетворяющих критериям поиска
     * @param filter Фильтр для поиска пользователей
     * @param pageable Пагинация
     * @return Страница с пользователями
     */
    public Page<UserByListDto> findAll(UserSearchFilter filter, Pageable pageable) {
        return userRepository.findAll(filter.toSpecification(), pageable)
                .map(this::convertUserToUserListDto);
    }

    /**
     * Преобразование DTO-объекта {@link UserRegistrationDto} в сущность {@link User}
     * @param dto Данные пользователя для регистрации
     * @return Сущность {@link User}
     */
    private User convertUserRegistrationDtoToUser(UserRegistrationDto dto) {
        User entity = new User();
        entity.setEmail(new Email(dto.getEmail()));
        entity.setPassword(encrypt(dto.getPassword()));
        entity.setLastName(dto.getLastName());
        entity.setFirstName(dto.getFirstName());
        entity.setDateOfBirth(LocalDate.of(
                dto.getYearOfBirth(),
                dto.getMonthOfBirth(),
                dto.getDayOfBirth()
        ));
        entity.setSex(dto.getSex());
        entity.setCity(dto.getCity());
        entity.setInterests(dto.getInterests());

        return entity;
    }

    /**
     * Преобразование сущности {@link User} в DTO-объект {@link UserPageDto}
     * @param entity Сущность {@link User}
     * @return Данные пользователя для персональной страницы
     */
    private UserPageDto convertUserToUserPageDto(User entity) {
        UserPageDto dto = new UserPageDto();
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setCity(entity.getCity());
        dto.setAge(entity.getDateOfBirth());
        dto.setInstagram(entity.getInstagram());
        dto.setWebsite(entity.getWebsite());
        dto.setPosts(entity.getPosts());

        return dto;
    }

    /**
     * Преобразование DTO-объекта {@link UserEditDto} в сущность {@link User}
     * @param dto Данные пользователя для редактирования учетной записи
     * @param entity Сущность {@link User}
     * @return Сущность {@link User}
     */
    private User convertUserEditDtoToUser(UserEditDto dto, User entity) {
        entity.setEmail(new Email(dto.getEmail()));
        entity.setPassword(encrypt(dto.getPassword()));
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setDateOfBirth(LocalDate.of(
                dto.getYearOfBirth(),
                dto.getMonthOfBirth(),
                dto.getDayOfBirth()
        ));
        entity.setSex(dto.getSex());
        entity.setCity(dto.getCity());
        entity.setInterests(dto.getInterests());
        entity.setInstagram(dto.getInstagram());
        entity.setWebsite(dto.getWebsite());

        return entity;
    }

    /**
     * Преобразование сущности {@link User} в DTO-объект {@link UserByListDto}
     * @param entity Сущность {@link User}
     * @return Данные пользователя для реестра пользователей
     */
    private UserByListDto convertUserToUserListDto(User entity) {
        UserByListDto dto = new UserByListDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setCity(entity.getCity());
        dto.setDateOfBirth(entity.getDateOfBirth());

        return dto;
    }

    private String encrypt(String password) {
        try {
            return new String(MessageDigest.getInstance("MD5").digest(password.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot encrypt password!");
        }
    }

}
