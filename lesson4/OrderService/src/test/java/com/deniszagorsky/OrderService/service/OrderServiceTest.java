package com.deniszagorsky.OrderService.service;

import com.deniszagorsky.OrderService.dao.OrderDAO;
import com.deniszagorsky.OrderService.domain.Customer;
import com.deniszagorsky.OrderService.domain.Order;
import com.deniszagorsky.OrderService.domain.OrderData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderDAO orderDAO;

    @Test
    void createOrder_newOrderGiven_created() {
        Customer customer = new Customer("Denis", "donnchadh.mr@gmail.com");
        Order order = new Order("Смартфон", 100_000);

        orderService.createOrder(new OrderData(customer, order));

        assertNotNull(customer.getId());
        assertNotNull(order.getId());
        assertEquals(customer, order.getCustomer());
        assertEquals(customer.getId(), order.getCustomerId());

        Mockito.verify(orderDAO, Mockito.times(1))
                .createOrder(customer, order, 0);
    }

}