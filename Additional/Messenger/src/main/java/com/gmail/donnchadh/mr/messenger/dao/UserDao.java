package com.gmail.donnchadh.mr.messenger.dao;

import com.gmail.donnchadh.mr.messenger.config.JpaConfig;
import com.gmail.donnchadh.mr.messenger.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.UUID;

public class UserDao {

    /**
     * Добавление нового экземпляра сущности User в БД в таблицу users
     * @param user Новый пользователь
     * @return Новый созданный экземпляр сущности User
     */
    public User addUser(User user) {
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
            entityManager.persist(user);
            entityTransaction.commit();
        } catch (Exception e) {
            entityTransaction.rollback();
            entityManager.close();
            throw e;
        }

        return user;
    }


    /**
     * Обновление значений атрибутов экземпляра сущности User, имеющего запись в БД в таблице users
     * @param user Пользователь
     */
    public void updateUser(User user) {
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
            entityManager.merge(user);
            entityTransaction.commit();
        } catch (Exception e) {
            entityTransaction.rollback();
            entityManager.close();
            throw e;
        }
    }

    /**
     * Поиск экземпляров сущности User в БД в таблице users в соответствии с переданным значением поля id
     * @param id Идентификатор пользователя
     * @return Существующий в БД в таблице users экземпляр сущности User, соответсвующий критерию поиска
     */
    public User findUserById(UUID id) {
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        User userFound = entityManager.find(User.class, id);
        if (userFound == null) { throw new NoResultException(); }
        else { return userFound; }
    }

    /**
     * Поиск экземпляра сущности User в БД в таблице users в соответствии с переданным значением поля email
     * @param email Адрес электронной почты пользователя
     * @return Существующий в БД в таблице users экземпляр сущности User, соотвествующий критерию поиска
     */
    public User findUserByEmail(String email) {
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root)
                .where(
                        criteriaBuilder.like(root.get("email"), email)
                );

        return entityManager.createQuery(query).getSingleResult();
    }

    /**
     * Поиск экземпляров сущности User в БД в таблице users в соответствии с переданными значениями полей
     * lastName, firstName, patronymic
     * @param lastName Фамилия
     * @param firstName Имя
     * @param patronymic Отчество
     * @return Список существующих в БД в таблице users экземпляров сущности User, соответсвующих критериям поиска
     */
    public List<User> findUsersByFio(String lastName, String firstName, String patronymic) {
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        Expression<String> lastNameEx = root.get("lastName");
        Expression<String> firstNameEx = root.get("firstName");
        Expression<String> patronymicEx = root.get("patronymic");
        query.select(root)
                .where(criteriaBuilder.and(lastNameEx.in(lastName),
                        firstNameEx.in(firstName),
                        patronymicEx.in(patronymic)));

        return entityManager.createQuery(query).getResultList();
    }

    /**
     * Удаление экземпляра сущности User из БД из таблицы users в соотвествии с переданным объектом user
     * @param user Пользователь
     */
    public void removeUser(User user) {
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
            entityManager.remove(entityManager.merge(user));
            entityTransaction.commit();
        } catch (Exception e) {
            entityTransaction.rollback();
            entityManager.close();
            throw e;
        }
    }

}
