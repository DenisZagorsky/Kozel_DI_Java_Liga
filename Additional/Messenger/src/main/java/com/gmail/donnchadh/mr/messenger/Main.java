package com.gmail.donnchadh.mr.messenger;

import com.gmail.donnchadh.mr.messenger.domain.Dialogue;
import com.gmail.donnchadh.mr.messenger.domain.Message;
import com.gmail.donnchadh.mr.messenger.domain.User;
import com.gmail.donnchadh.mr.messenger.service.MessengerService;
import com.gmail.donnchadh.mr.messenger.service.UserService;

import java.util.List;

public class Main {
    private static final UserService USER_SERVICE = new UserService();
    private static final MessengerService MESSENGER_SERVICE = new MessengerService();

    public static void main(String[] args) {
        /**
         * 1. Регистрация новых пользователей:
         *
         * Примечание: происходит проверка пользователя на наличие "учетной записи" с помощью email
         * Если пользователь уже находится в БД (уже существует учетная запись с заданным email)
         * => падает исключение UserAlreadyExistsException
         */
        System.out.println("1.");
        User denis = new User("donnchadh.mr@gmail.com", "Загорский", "Денис",
                "Игоревич", 1998, 2, 4);
        User ivan = new User("ivan@gmail.com", "Иванов", "Иван",
                "Иванович", 1998, 5, 26);
        User kate = new User("kate@gmail.com", "Олексенко", "Екатерина",
                "Андреевна", 1998, 4, 17);

        USER_SERVICE.createUser(denis);
        USER_SERVICE.createUser(ivan);
        USER_SERVICE.createUser(kate);

        //Проверка на присвоение id:
        System.out.println("Денис id: " + denis.getId());
        System.out.println("Иван id: " + ivan.getId());
        System.out.println("Екатерина id: " + ivan.getId());

        /**
         * 2. Редактирование полей пользователей:
         */
        System.out.println("\n2.");
        denis.setPersonalInf("I'm on my way to becoming a software engineer");
        ivan.setPersonalInf("I'd better tell u in person!");

        USER_SERVICE.editUser(denis);
        USER_SERVICE.editUser(ivan);

        //Проверка на успешное редактирование полей:
        System.out.println("Денис, поле \"Информация о пользователе\": " + denis.getPersonalInf());
        System.out.println("Иван, поле \"Информация о пользователе\": " + ivan.getPersonalInf());
        System.out.println("Екатерина, поле \"Отчество\": " + kate.getPatronymic());

        /**
         * 3. Поиск пользователей по ФИО:
         */
        System.out.println("\n3.");
        List<User> users = USER_SERVICE.getUsersByFio("Загорский", "Денис", "Игоревич");
        users.stream().forEach(System.out::println);

        users = USER_SERVICE.getUsersByFio("Олексенко", "Екатерина", "Андреевна");
        users.stream().forEach(System.out::println);

        /**
         * 4. Отправка сообщений:
         *
         * Примечание: происходит проверка пользователя-получателя на наличие "учетной записи" с помощью id
         * Если записи с данным пользователем в БД нет (не существует учетная запись с заданным id)
         * => выпадает исключение UserDoesNotExistException
         */
        System.out.println("\n4.");
        //Диалог между denis и ivan:
        String body1 = "What's up?";
        Message message1 = new Message(body1, denis, ivan);
        MESSENGER_SERVICE.sendMessage(message1);
        String body2 = "Not much";
        Message message2 = new Message(body2, ivan, denis);
        MESSENGER_SERVICE.sendMessage(message2);
        String body3 = "Yeah, same";
        Message message3 = new Message(body3, denis, ivan);
        MESSENGER_SERVICE.sendMessage(message3);

        //Диалог между kate и ivan:
        String body4 = "Have u heard the news?!";
        Message message4 = new Message(body4, kate, ivan);
        MESSENGER_SERVICE.sendMessage(message4);
        String body5 = "Not really";
        Message message5 = new Message(body5, ivan, kate);
        MESSENGER_SERVICE.sendMessage(message5);
        String body6 = "You're missing out on much";
        Message message6 = new Message(body6, kate, ivan);
        MESSENGER_SERVICE.sendMessage(message6);

        //Проверка на создание записи в таблице dialogue
        //а также на присвоение id данной записи каждому сообщению (dialogue_id в таблице message),
        //т.к. они имеют одинаковых пользователей => принадлежат одному диалогу:
        System.out.println("dialogue_id для message1: " + message1.getDialogue().getId());
        System.out.println("dialogue_id для message2: " + message2.getDialogue().getId());
        System.out.println("dialogue_id для message3: " + message1.getDialogue().getId());

        /**
         * 5. Редактирование текста сообщения:
         */
        message1.setBody("[Edited] Up to much?");
        MESSENGER_SERVICE.editMessage(message1);

        /**
         * 6. Получение списка диалогов пользователя:
         */
        System.out.println("\n6.");
        List<Dialogue> dialoguesDenis = MESSENGER_SERVICE.getAllDialogues(denis);
        System.out.println("Диалоги пользователя denis:");
        dialoguesDenis.stream().forEach(System.out::println);

        List<Dialogue> dialoguesIvan = MESSENGER_SERVICE.getAllDialogues(ivan);
        System.out.println("\nДиалоги пользователя ivan:");
        dialoguesIvan.stream().forEach(System.out::println);

        List<Dialogue> dialoguesKate = MESSENGER_SERVICE.getAllDialogues(kate);
        System.out.println("\nДиалоги пользователя kate:");
        dialoguesKate.stream().forEach(System.out::println);

        /**
         * 7. Получение содержимого диалога:
         */
        System.out.println("\n7.");
        List<Message> messages = MESSENGER_SERVICE.getAllMessagesFromDialogue(dialoguesDenis.get(0));
        messages.stream().forEach(System.out::println);

        /**
         * 8.  Удаление пользователя:
         *
         * Примечание: удаление пользователя также "тянет" за собой удаление всех диалогов с сообщениями,
         * в которых пользователь участвовал
         */
        USER_SERVICE.deleteUser(denis);
    }

}
