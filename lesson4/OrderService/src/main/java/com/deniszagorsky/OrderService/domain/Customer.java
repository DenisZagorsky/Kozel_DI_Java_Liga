package com.deniszagorsky.OrderService.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NonNull;

@Data
public class Customer {

    /**
     * Идентификатор покупателя
     */
    @JsonIgnore
    private Integer id;

    /**
     * Имя покупателя
     */
    @NonNull
    private String name;

    /**
     * Адрес электронной почты
     */
    @NonNull
    private String emailAddress;

}
