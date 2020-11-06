package com.gmail.donnchadh.mr.messenger.dao;

import com.gmail.donnchadh.mr.messenger.config.JpaConfig;
import com.gmail.donnchadh.mr.messenger.domain.Dialogue;
import com.gmail.donnchadh.mr.messenger.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class DialogueDao {

    /**
     * Добавление нового экземпляра сущности Dialogue в БД в таблицу dialogue
     * @param dialogue Новый диалог
     * @return Новый созданный экземпляр сущности Dialogue
     */
    public Dialogue addDialogue(Dialogue dialogue) {
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
            entityManager.persist(dialogue);
            entityTransaction.commit();
        } catch (Exception e) {
            entityTransaction.rollback();
            entityManager.close();
            throw e;
        }

        return dialogue;
    }

    /**
     * Поиск экземпляра сущности Dialogue в БД в таблице dialogue в соответствии с переданными значениями полей firstUser и secondUser
     * @param user Пользователь
     * @param contact Пользователь
     * @return Существующий в БД в таблице dialogue экземпляр сущности Dialogue, соответсвующий критериям поиска
     */
    public Dialogue findDialogue(User user, User contact) {
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        List<User> users = new ArrayList<>(){{
            add(user);
            add(contact);
        }};
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Dialogue> query = criteriaBuilder.createQuery(Dialogue.class);
        Root<Dialogue> root = query.from(Dialogue.class);
        Expression<User> firstUserEx = root.get("firstUser");
        Expression<User> secondUserEx = root.get("secondUser");
        query.select(root)
                .where(
                        criteriaBuilder.and(firstUserEx.in(users), secondUserEx.in(users))
                );

        return entityManager.createQuery(query).getSingleResult();
    }

    /**
     * Поиск экземпляров сущности Dialogue в БД в таблице dialogue в соответствии с переданным значением поля firstUser или secondUser
     * @param user Пользователь
     * @return Список существующих в БД в таблице dialogue экземпляров сущности Dialogue, соответствующих критерию поиска
     */
    public List<Dialogue> findAllDialoguesByUser(User user) {
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Dialogue> query = criteriaBuilder.createQuery(Dialogue.class);
        Root<Dialogue> root = query.from(Dialogue.class);
        Expression<User> firstUserId = root.get("firstUser");
        Expression<User> secondUserId = root.get("secondUser");
        query.select(root)
                .where(criteriaBuilder.or(firstUserId.in(user), secondUserId.in(user)));

        return entityManager.createQuery(query).getResultList();
    }

    /**
     * Удаление экземпляров сущности Dialogue из БД из таблицы dialogue в соотвествии с переданным значением поля firstUser или secondUser
     * @param user Пользователь
     */
    public void removeAllDialoguesByUser(User user) {
        List<Dialogue> dialogues = findAllDialoguesByUser(user);
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
            for (Dialogue dialogue : dialogues) { entityManager.remove(entityManager.merge(dialogue)); }
            entityTransaction.commit();
        } catch (Exception e) {
            entityTransaction.rollback();
            entityManager.close();
            throw e;
        }
    }

}
