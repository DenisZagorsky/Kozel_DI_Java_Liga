package com.deniszagorsky.socialnetwork.domain.embeddable;

import com.deniszagorsky.socialnetwork.exception.EmailNotValidException;
import com.deniszagorsky.socialnetwork.util.EmailValidator;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

/**
 * Электронная почта
 */

@Data
@NoArgsConstructor
@Embeddable
public class Email {

    /**
     * Адрес электронной почты
     */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * Валидатор электронной почты
     */
    @Transient
    private static final EmailValidator EMAIL_VALIDATOR = new EmailValidator();

    public Email(String email) {
        this.email = email;
        if (EMAIL_VALIDATOR.isValid(email)) {
            this.email = email;
        } else {
            throw new EmailNotValidException();
        }
    }

    @Override
    public String toString() { return email; }

}
