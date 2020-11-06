package com.gmail.donnchadh.mr.messenger.dao;

import com.gmail.donnchadh.mr.messenger.config.JpaConfig;
import com.gmail.donnchadh.mr.messenger.domain.Dialogue;
import com.gmail.donnchadh.mr.messenger.domain.Message;
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

public class MessageDao {

    /**
     * Добавление нового экземпляра сущности Message в БД в таблицу message
     * @param message Новое сообщение
     * @return Новый созданный экземпляр сущности Message
     */
    public Message addMessage(Message message) {
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
            entityManager.persist(message);
            entityTransaction.commit();
        } catch (Exception e) {
            entityTransaction.rollback();
            entityManager.close();
            throw e;
        }

        return message;
    }

    /**
     * Обновление значений атрибутов экземпляра сущности Message, имеющего запись в БД в таблице message
     * @param message Сообщение
     */
    public void updateMessage(Message message) {
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
            entityManager.merge(message);
            entityTransaction.commit();
        } catch (Exception e) {
            entityTransaction.rollback();
            entityManager.close();
            throw e;
        }
    }

    /**
     * Поиск экземпляров сущности Message в БД в таблице message в соответствии с переданным значением атрибута id
     * @param id Идентификатор сообщения
     * @return Существующий в БД в таблице message экземпляр сущности Message, соответсвующий критериям поиска
     */
    public Message findMessageById(UUID id) {
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        Message messageFound = entityManager.find(Message.class, id);
        if (messageFound == null) { throw new NoResultException(); }
        else { return messageFound; }
    }

    /**
     * Поиск экземпляров сущности Message в БД в таблице message в соответствии с переданным значением поля dialogue
     * @param dialogue Диалог
     * @return Список существующих в БД в таблице message экземпляров сущности Message, соответсвующих критерию поиска
     */
    public List<Message> findAllMessagesFromDialogue(Dialogue dialogue) {
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Message> query = criteriaBuilder.createQuery(Message.class);
        Root<Message> root = query.from(Message.class);
        query.select(root)
                .where(root.get("dialogue").in(dialogue))
                .orderBy(criteriaBuilder.asc(root.get("dateSent")));

        return entityManager.createQuery(query).getResultList();
    }

    /**
     * Поиск экземпляров сущности Message в БД в таблице message в соответствии с переданным значением поля author или recipient
     * @param user Пользователь
     * @return Список существующих в БД в таблице message экземпляров сущности Message, соответсвующих критерию поиска
     */
    public List<Message> findAllMessagesByUser(User user) {
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Message> query = criteriaBuilder.createQuery(Message.class);
        Root<Message> root = query.from(Message.class);
        Expression<User> author = root.get("author");
        Expression<User> recipient = root.get("recipient");
        query.select(root)
                .where(criteriaBuilder.or(author.in(user), recipient.in(user)));
        return entityManager.createQuery(query).getResultList();
    }

    /**
     * Удаление экземпляра сущности Message из БД из таблицы message в соотвествии с переданным объектом message
     * @param message Сообщение
     */
    public void removeMessage(Message message) {
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
            entityManager.remove(message);
            entityTransaction.commit();
        } catch (Exception e) {
            entityTransaction.rollback();
            entityManager.close();
            throw e;
        }
    }

    /**
     * Удаление экземпляров сущностей Message из БД из таблицы message в соотвествии с переданным полем author или recipient
     * @param user Пользователь
     */
    public void removeAllMessagesByUser(User user) {
        List<Message> messages = findAllMessagesByUser(user);
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
            for (Message message : messages) { entityManager.remove(entityManager.merge(message)); }
            entityTransaction.commit();
        } catch (Exception e) {
            entityTransaction.rollback();
            entityManager.close();
            throw e;
        }
    }

}
