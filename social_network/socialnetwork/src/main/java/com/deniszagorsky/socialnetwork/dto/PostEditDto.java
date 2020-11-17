package com.deniszagorsky.socialnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * DTO данных поста для редактирования
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostEditDto {

    /**
     * Тело поста
     */
    @NotEmpty
    @Length(max = 280)
    private String body;

}
