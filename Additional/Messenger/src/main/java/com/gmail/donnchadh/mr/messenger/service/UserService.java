package com.gmail.donnchadh.mr.messenger.service;

import com.gmail.donnchadh.mr.messenger.dao.UserDao;
import com.gmail.donnchadh.mr.messenger.domain.User;
import com.gmail.donnchadh.mr.messenger.exception.UserAlreadyExistsException;
import com.gmail.donnchadh.mr.messenger.exception.UserDoesNotExistException;
import com.gmail.donnchadh.mr.messenger.exception.UserNotFoundException;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.UUID;

public class UserService {
    private static final UserDao USER_DAO = new UserDao();

    /**
     * Создание нового пользователя
     * @param user Новый пользователь
     * @return Нового созданного пользователя
     */
    public User createUser(User user) {
        try {
            if (getUserByEmail(user.getEmail()) != null) {
                throw new UserAlreadyExistsException();
            }
        } catch (UserNotFoundException e) {}

        User userCreated = USER_DAO.addUser(user);
        return userCreated;
    }

    /**
     * Редактирование полей существующего пользователя
     * @param user Существующий пользователь
     */
    public void editUser(User user) {
        try {
            getUserById(user.getId());
        } catch (NoResultException e) {
            throw new UserDoesNotExistException();
        }

        USER_DAO.updateUser(user);
    }

    /**
     * Получение пользователя по id
     * @param id Идентификатор пользователя
     * @return Пользователь, соотвествующий критериям поиска
     */
    public User getUserById(UUID id) {
        User user;
        try {
            user = USER_DAO.findUserById(id);
        } catch (NoResultException e) {
            throw new UserNotFoundException();
        }

        return user;
    }

    /**
     * Получение пользователя по email
     * @param email Адрес электронной почты пользователя
     * @return Пользователь, имеющий соответствующий переданному параметру email
     */
    protected User getUserByEmail(String email) {
        User user;
        try {
            user = USER_DAO.findUserByEmail(email);
        } catch (NoResultException e) {
            throw new UserNotFoundException();
        }

        return user;
    }

    /**
     * Получение пользователей по ФИО
     * @param lastName Фамилия пользователя
     * @param firstName Имя пользователя
     * @param patronymic Отчество пользователя
     * @return Список пользователей, соотвествующих критериям поиска
     */
    public List<User> getUsersByFio(String lastName, String firstName, String patronymic) {
        List<User> users = USER_DAO.findUsersByFio(lastName, firstName, patronymic);
        if (users.isEmpty()) { throw new UserNotFoundException(); }
        return users;
    }

    /**
     * Удаление пользователя БД
     * @param user Пользователь
     */
    public void deleteUser(User user) {
        try {
            getUserById(user.getId());
        } catch (NoResultException e) {
            throw new UserDoesNotExistException();
        }
        MessengerService messengerService = new MessengerService();
        messengerService.deleteAllDialoguesByUser(user);
        USER_DAO.removeUser(user);
    }

}
