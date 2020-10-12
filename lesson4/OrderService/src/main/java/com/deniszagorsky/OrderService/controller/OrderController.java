package com.deniszagorsky.OrderService.controller;

import com.deniszagorsky.OrderService.domain.Order;
import com.deniszagorsky.OrderService.domain.OrderData;
import com.deniszagorsky.OrderService.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Серелизация полученных из {@link OrderService#getAllInfOnOrders()} данных по заказам
     *
     * @return JSON
     */
    @GetMapping("/order")
    public List<Map<String, Object>> getAllInfOnOrders() { return orderService.getAllInfOnOrders(); }

    /**
     * Серелизация полученных из {@link OrderService#getAllInfOnCustomers()} данных по покупателям
     *
     * @return JSON
     */
    @GetMapping("/customer")
    public List<Map<String, Object>> getAllInfOnCustomers() { return orderService.getAllInfOnCustomers(); }

    /**
     * Десерелизация сущности {@link OrderData} и ее передача в {@link OrderService#createOrder(OrderData)}
     * для создания сущности {@link Order}
     *
     * @param orderData POJO
     * @return код состояния HTTP 200 OK
     */
    @PostMapping("/order")
    public ResponseEntity<?> createOrder(@RequestBody OrderData orderData) {
        orderService.createOrder(orderData);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
