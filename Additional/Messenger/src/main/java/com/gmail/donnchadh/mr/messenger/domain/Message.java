package com.gmail.donnchadh.mr.messenger.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Table(name = "message")
public class Message {

    /**
     * Идентификатор сообщения
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    /**
     * Тело сообщения
     */
    @Column(name = "body", nullable = false)
    private String body;

    /**
     * Дата и время отправки сообщения
     */
    @Column(name = "date_sent", nullable = false)
    private LocalDateTime dateSent;

    /**
     * Автор сообщения
     */
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    /**
     * Получатель сообщения
     */
    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    private User recipient;

    /**
     * Диалог, которому принадлежит сообщение
     */
    @ManyToOne
    @JoinColumn(name = "dialogue_id", nullable = false)
    private Dialogue dialogue;

    public Message() {}

    public Message(String body, User author, User recipient) {
        this.body = body;
        this.author = author;
        this.recipient = recipient;
        setDateSent();
    }

    public UUID getId() { return id; }

    public String getBody() { return body; }

    public void setBody(String body) { this.body = body; }

    public LocalDateTime getDateSent() { return dateSent; }

    public void setDateSent() { dateSent = LocalDateTime.now(); }

    public User getAuthor() { return author; }

    public void setAuthor(User user) { author = user; }

    public User getRecipient() { return recipient; }

    public void setRecipient(User user) { recipient = user; }

    public Dialogue getDialogue() { return dialogue; }

    public void setDialogue(Dialogue dialogue) { this.dialogue = dialogue; }

    @Override
    public String toString() {
        String message = "[%s] %s: %s";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format(message, dateSent.format(formatter), author.getFirstName(), body);
    }

}

