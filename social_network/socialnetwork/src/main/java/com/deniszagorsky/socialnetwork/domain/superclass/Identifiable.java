package com.deniszagorsky.socialnetwork.domain.superclass;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

/**
 * Базовый класс сущности
 */

@Data
@MappedSuperclass
public abstract class Identifiable {

    /**
     * Идентификатор сущности
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", nullable = false, length = 36, updatable = false)
    private UUID id;

}
