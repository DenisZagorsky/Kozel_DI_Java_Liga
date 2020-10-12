package com.deniszagorsky.OrderService.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NonNull;

@Data
public class Order {

    /**
     * Идентификатор заказа
     */
    @JsonIgnore
    private Integer id;

    /**
     * Наименование заказа
     */
    @NonNull
    private String name;

    /**
     * Цена
     */
    @NonNull
    private Integer price;

    /**
     * Покупатель
     */
    @JsonIgnore
    private Customer customer;

    /**
     * Идентификатор покупателя
     */
    @JsonIgnore
    private Integer customerId;

}
