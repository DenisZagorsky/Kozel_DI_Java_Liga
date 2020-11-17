package com.deniszagorsky.socialnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO данных пользователя для его идентификации
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserIdDto {

    /**
     * Идентификатор
     */
    private UUID id;

}
