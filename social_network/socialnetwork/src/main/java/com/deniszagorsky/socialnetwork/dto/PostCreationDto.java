package com.deniszagorsky.socialnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * DTO данных поста для создания
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCreationDto {

    /**
     * Тело поста
     */
    @NotEmpty
    @Length(max = 280)
    private String body;

}
