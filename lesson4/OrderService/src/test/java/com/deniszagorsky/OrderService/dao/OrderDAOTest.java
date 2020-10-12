package com.deniszagorsky.OrderService.dao;

import com.deniszagorsky.OrderService.domain.Customer;
import com.deniszagorsky.OrderService.domain.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class OrderDAOTest {

    private static final String CUSTOMER_DB = "\"Customer\"";
    private static final String ORDER_DB = "\"Order\"";

    private static final String SELECT_QUERY = "SELECT * FROM PUBLIC.%s;";
    private static final String INSERT_QUERY = "INSERT INTO PUBLIC.%s VALUES(%s);";

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void getAllInfOnCustomers_tableCreated_returned() {
        Customer customer = new Customer("Denis", "donnchadh.mr@gmail.com");
        customer.setId(1);

        jdbcTemplate.execute(String.format(INSERT_QUERY, CUSTOMER_DB, customer.getId() + ", \'"
                + customer.getName() + "\', \'"
                + customer.getEmailAddress() + "\'"));

        List<Map<String, Object>> list = orderDAO.getAllInfOnCustomers();

        assertEquals(customer.getId(), list.get(0).get("ID"));
        assertEquals(customer.getName(), list.get(0).get("NAME"));
        assertEquals(customer.getEmailAddress(), list.get(0).get("EMAIL_ADDRESS"));
    }

    @Test
    void getAllInfOnOrders_tablesCreated_returned() {
        Customer customer = new Customer("Denis", "donnchadh.mr@gmail.com");
        customer.setId(1);

        Order order = new Order("Cмартфон", 100_000);
        order.setId(1);
        order.setCustomer(customer);
        order.setCustomerId(customer.getId());

        jdbcTemplate.execute(String.format(INSERT_QUERY, CUSTOMER_DB, customer.getId() + ", \'"
                + customer.getName() + "\', \'"
                + customer.getEmailAddress() + "\'"));

        jdbcTemplate.execute(String.format(INSERT_QUERY, ORDER_DB, order.getId() + ", \'"
                + order.getName() + "\', "
                + order.getPrice() + ", "
                + order.getCustomerId()));

        List<Map<String, Object>> list = orderDAO.getAllInfOnOrders();

        assertEquals(order.getId(), list.get(0).get("ID"));
        assertEquals(order.getName(), list.get(0).get("NAME"));
        assertEquals(order.getPrice(), list.get(0).get("PRICE"));
        assertEquals(order.getCustomerId(), list.get(0).get("CUSTOMER_ID"));
    }

    @Test
    void createOrder_newOrderGiven_DBCreated() {
        Customer customer = new Customer("Denis", "donnchadh.mr@gmail.com");
        customer.setId(1);

        Order order = new Order("Cмартфон", 100_000);
        order.setId(1);
        order.setCustomer(customer);
        order.setCustomerId(customer.getId());
        int isCustomerNew = 0;

        orderDAO.createOrder(customer, order, isCustomerNew);

        List<Map<String, Object>> listCustomer = jdbcTemplate.queryForList(String.format(SELECT_QUERY, CUSTOMER_DB));
        assertEquals(customer.getId(), listCustomer.get(0).get("ID"));
        assertEquals(customer.getName(), listCustomer.get(0).get("NAME"));
        assertEquals(customer.getEmailAddress(), listCustomer.get(0).get("EMAIL_ADDRESS"));

        List<Map<String, Object>> listOrder = jdbcTemplate.queryForList(String.format(SELECT_QUERY, ORDER_DB));
        assertEquals(order.getId(), listOrder.get(0).get("ID"));
        assertEquals(order.getName(), listOrder.get(0).get("NAME"));
        assertEquals(order.getPrice(), listOrder.get(0).get("PRICE"));
        assertEquals(order.getCustomerId(), listOrder.get(0).get("CUSTOMER_ID"));
    }

}