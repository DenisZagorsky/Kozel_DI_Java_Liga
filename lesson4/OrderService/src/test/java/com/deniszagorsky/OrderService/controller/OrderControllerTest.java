package com.deniszagorsky.OrderService.controller;

import com.deniszagorsky.OrderService.domain.Customer;
import com.deniszagorsky.OrderService.domain.Order;
import com.deniszagorsky.OrderService.domain.OrderData;
import com.deniszagorsky.OrderService.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class OrderControllerTest {

    @Autowired
    OrderController orderController;

    @MockBean
    OrderService orderService;

    @Test
    void getAllInfOnOrders_none_methodGetAllInfOnOrdersInvoked() {
        orderController.getAllInfOnOrders();

        Mockito.verify(orderService, Mockito.times(1))
                .getAllInfOnOrders();
    }

    @Test
    void getAllInfOnCustomers_none_methodGetAllInfOnCustomersInvoked() {
        orderController.getAllInfOnCustomers();

        Mockito.verify(orderService, Mockito.times(1))
                .getAllInfOnCustomers();
    }

    @Test
    void createOrder_orderDataCreated_methodCreateOrderInvoked() {
        Customer customer = new Customer("Denis", "donnchadh.mr@gmail.com");
        Order order = new Order("Смартфон", 100_000);
        OrderData orderData = new OrderData(customer, order);

        orderController.createOrder(orderData);

        Mockito.verify(orderService, Mockito.times(1))
                .createOrder(orderData);
    }
}