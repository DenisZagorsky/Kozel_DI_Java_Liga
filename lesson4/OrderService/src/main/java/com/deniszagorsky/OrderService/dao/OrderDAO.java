package com.deniszagorsky.OrderService.dao;

import com.deniszagorsky.OrderService.domain.Customer;
import com.deniszagorsky.OrderService.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class OrderDAO {

    private static final String CUSTOMER_DB = "\"Customer\"";
    private static final String ORDER_DB = "\"Order\"";

    private static final String SELECT_QUERY = "SELECT * FROM PUBLIC.%s;";
    private static final String INSERT_QUERY = "INSERT INTO PUBLIC.%s VALUES(%s);";

    @Autowired
    JdbcTemplate jdbcTemplate;

    /** Получение из БД всех данных по покупателям (таблица "Customer")
     *
     * @return Список с мапами, где в качестве key выступает наименование атрибута, в качестве value – значение атрибута
     */
    public List<Map<String, Object>> getAllInfOnCustomers() {
        return jdbcTemplate.queryForList(String.format(SELECT_QUERY, CUSTOMER_DB));
    }

    /** Получение из БД всех данных по заказам (таблица "Order")
     *
     * @return Список с мапами, где в качестве key выступает наименование атрибута, в качестве value – значение атрибута
     */
    public List<Map<String, Object>> getAllInfOnOrders() {
        return jdbcTemplate.queryForList(String.format(SELECT_QUERY, ORDER_DB));
    }

    /**
     * Создание сущности {@link Order} в БД в таблице "Order"
     *
     * @param customer Покупатель
     * @param order Заказ
     * @param isCustomerNew 0 в случае, если покупателя нет в БД; customer.id, если покупатель присутствует в БД
     */
    public void createOrder(Customer customer, Order order, int isCustomerNew) {
        if (isCustomerNew == 0) { createCustomer(customer); }

        jdbcTemplate.execute(String.format(INSERT_QUERY, ORDER_DB, order.getId() + ", \'"
                + order.getName() + "\', "
                + order.getPrice() + ", "
                + order.getCustomerId()));
    }

    /**
     * Создание сущности {@link Customer} в БД в таблице "Customer"
     *
     * @param customer Покупатель
     */
    private void createCustomer(Customer customer) {
        jdbcTemplate.execute(String.format(INSERT_QUERY, CUSTOMER_DB, customer.getId() + ", \'"
                + customer.getName() + "\', \'"
                + customer.getEmailAddress() + "\'"));
    }

}
