package com.gmail.donnchadh.mr.messenger.service;

import com.gmail.donnchadh.mr.messenger.dao.DialogueDao;
import com.gmail.donnchadh.mr.messenger.dao.MessageDao;
import com.gmail.donnchadh.mr.messenger.domain.Dialogue;
import com.gmail.donnchadh.mr.messenger.domain.Message;
import com.gmail.donnchadh.mr.messenger.domain.Role;
import com.gmail.donnchadh.mr.messenger.domain.User;
import com.gmail.donnchadh.mr.messenger.exception.*;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MessengerService {
    private static final MessageDao MESSAGE_DAO = new MessageDao();
    private static final DialogueDao DIALOGUE_DAO = new DialogueDao();

    /**
     * Отправка нового сообщения
     * @param message Новое сообщение
     * @return новое созданное сообщение
     */
    public Message sendMessage(Message message) {
        try {
            UserService userService = new UserService();
            userService.getUserById(message.getRecipient().getId());
        } catch (NoResultException e) {
            throw new UserDoesNotExistException();
        }

        Dialogue dialogue = new Dialogue(message.getAuthor(), message.getRecipient());
        try {
            dialogue = getDialogue(message.getAuthor(), message.getRecipient());
        } catch (DialogueNotFoundException e) {
            createDialogue(dialogue);
        }
        message.setDialogue(dialogue);
        Message messageCreated = MESSAGE_DAO.addMessage(message);
        return messageCreated;
    }

    /**
     * Создание нового диалога
     * @param dialogue Новый диалог
     * @return Новый созданный диалог
     */
    private Dialogue createDialogue(Dialogue dialogue) {
        Dialogue dialogueCreated = DIALOGUE_DAO.addDialogue(dialogue);
        return dialogueCreated;
    }

    /**
     * Редактирование полей существующего сообщение
     * @param message Существующее сообщение
     */
    public void editMessage(Message message) {
        try {
            getMessageById(message.getId());
        } catch (NoResultException e) {
            throw new MessageDoesNotExistException();
        }

        MESSAGE_DAO.updateMessage(message);
    }

    /**
     * Получение сообщения по id
     * @param id Идентификатор сообщения
     * @return Сообщение, соотвествующее критериям поиска
     */
    public Message getMessageById(UUID id) {
        Message message;
        try {
        message = MESSAGE_DAO.findMessageById(id);
        } catch (NoResultException e) {
            throw new MessageNotFoundException();
        }

        return message;
    }

    /**
     * Получение сообщений из диалога
     * @param dialogue Диалог
     * @return Список сообщений, соответсующих переданному диалогу
     */
    public List<Message> getAllMessagesFromDialogue(Dialogue dialogue) {
        List<Message> messages = MESSAGE_DAO.findAllMessagesFromDialogue(dialogue);
        if (messages.isEmpty()) { throw new MessageNotFoundException(); }
        return messages;
    }

    /**
     * Получение диалога по пользователям
     * @param user Пользователь
     * @param contact Пользователь
     * @return Диалог, соответсвующий критериям поиска
     */
    public Dialogue getDialogue(User user, User contact) {
        Dialogue dialogue;
        try {
        dialogue = DIALOGUE_DAO.findDialogue(user, contact);
        } catch (NoResultException e) {
            throw new DialogueNotFoundException();
        }

        return dialogue;
    }

    /**
     * Получение списка диалогов пользователя
     * @param user Пользователь
     * @return Список диалогов
     */
    public List<Dialogue> getAllDialogues(User user) {
        List<Dialogue> allDialogues = DIALOGUE_DAO.findAllDialoguesByUser(user);
        if (allDialogues.isEmpty()) { throw new DialogueNotFoundException(); }
        allDialogues = allDialogues.stream().map(dialogue -> {
            if (dialogue.getFirstUser().getId().equals(user.getId())) {
                dialogue.getFirstUser().setRole(Role.USER);
                dialogue.getSecondUser().setRole(Role.CONTACT);
            } else {
                dialogue.getFirstUser().setRole(Role.CONTACT);
                dialogue.getSecondUser().setRole(Role.USER);
            }
            return dialogue;
        }).collect(Collectors.toList());

        return allDialogues;
    }


    /**
     * Удаление всех диалогов с сообщениями, в которых участвовал пользователь
     * @param user Пользователь
     */
    protected void deleteAllDialoguesByUser(User user) {
        try {
            getAllDialogues(user);
        } catch (NoResultException e) {
            throw new MessageDoesNotExistException();
        }
        MESSAGE_DAO.removeAllMessagesByUser(user);
        DIALOGUE_DAO.removeAllDialoguesByUser(user);
    }


}
