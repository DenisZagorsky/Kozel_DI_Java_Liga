package com.gmail.donnchadh.mr.messenger.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "dialogue")
public class Dialogue {

    /**
     * Идентификатор диалога
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
     * Идентификатор первого участника диалога
     */
    @ManyToOne
    @JoinColumn(name = "first_user_id", nullable = false)
    private User firstUser;

    /**
     * Идентификатор второго участника диалога
     */
    @ManyToOne
    @JoinColumn(name = "second_user_id", nullable = false)
    private User secondUser;

    public Dialogue() {}

    public Dialogue(User firstUser, User secondUser) {
        this.firstUser = firstUser;
        this.secondUser = secondUser;
    }

    public UUID getId() { return id; }

    public User getFirstUser() { return firstUser; }

    public void setFirstUser(User firstUser) { this.firstUser = firstUser; }

    public User getSecondUser() { return secondUser; }

    public void setSecondUser(User secondUser) { this.secondUser = secondUser; }

    @Override
    public String toString() {
        if (firstUser.getRole() == Role.USER) {
            return secondUser.getLastName() + " " + secondUser.getFirstName();
        } else {
            return  firstUser.getLastName() + " " + firstUser.getFirstName();
        }
    }

}
