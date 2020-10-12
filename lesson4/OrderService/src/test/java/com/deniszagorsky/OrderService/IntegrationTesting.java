package com.deniszagorsky.OrderService;

import com.deniszagorsky.OrderService.domain.Customer;
import com.deniszagorsky.OrderService.domain.Order;
import com.deniszagorsky.OrderService.domain.OrderData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTesting {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @Test
    void createOrder_orderDataCreated_DBCreated() throws Exception {
        Customer customer = new Customer("Denis", "donnchadh.mr@gmail.com");
        Order order = new Order("Смартфон", 100_000);
        OrderData orderData = new OrderData(customer, order);

        mvc.perform(post("http://localhost:8080/api/v1/order/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderData)))
                .andExpect(status().isOk());
    }

}
