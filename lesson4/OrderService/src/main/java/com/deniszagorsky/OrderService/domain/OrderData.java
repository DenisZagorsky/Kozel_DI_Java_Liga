package com.deniszagorsky.OrderService.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public class OrderData {

    /**
     * Покупатель
     */
    @NonNull
    private Customer customer;

    /**
     * Заказ
     */
    @NonNull
    private Order order;

}
