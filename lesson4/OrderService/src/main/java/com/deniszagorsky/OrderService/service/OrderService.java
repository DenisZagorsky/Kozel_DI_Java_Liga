package com.deniszagorsky.OrderService.service;

import com.deniszagorsky.OrderService.dao.OrderDAO;
import com.deniszagorsky.OrderService.domain.Customer;
import com.deniszagorsky.OrderService.domain.Order;
import com.deniszagorsky.OrderService.domain.OrderData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    /**
     * Счетчик покупателей
     */
    private static int countCustomer;

    /**
     * Счетчик заказов
     */
    private static int countOrder;

    @Autowired
    OrderDAO orderDAO;

    /** Получение из БД всех данных по покупателям (таблица "Customer")
     *
     * @return Список с мапами, где в качестве key выступает наименование атрибута, в качестве value – значение атрибута
     */
    public List<Map<String, Object>> getAllInfOnCustomers() { return orderDAO.getAllInfOnCustomers(); }

    /** Получение из БД всех данных по заказам (таблица "Order")
     *
     * @return Список с мапами, где в качестве key выступает наименование атрибута, в качестве value – значение атрибута
     */
    public List<Map<String, Object>> getAllInfOnOrders() { return orderDAO.getAllInfOnOrders(); }

    /** Создание сущности {@link Order} и ее передача в {@link OrderDAO#createOrder(Customer, Order, int)}
     * для создания соответствующей сущности в БД в таблице "Order"
     *
     * @param orderData
     */
    public void createOrder(OrderData orderData) {
        Customer customer = orderData.getCustomer();
        Order order = orderData.getOrder();

        int isCustomerNew = isCustomerNew(customer);
        initializeCustomerId(customer, isCustomerNew);

        order.setId(++countOrder);
        order.setCustomer(customer);
        order.setCustomerId(customer.getId());
        orderDAO.createOrder(customer, order, isCustomerNew);
    }

    /**
     * Инициализация id покупателя
     *
     * @param customer покупатель
     * @param isCustomerNew 0 в случае, если покупателя нет в БД; customer.id, если покупатель присутствует в БД
     */
    private void initializeCustomerId(Customer customer, int isCustomerNew) {
        if (isCustomerNew > 0) { customer.setId(isCustomerNew); }
        else if (isCustomerNew == 0) { customer.setId(++countCustomer); }
    }

    /**
     * Проверка сущности {@link Customer} на ее наличие в БД в таблице "Customer"
     *
     * @param customer покупатель
     * @return 0 в случае, если покупателя нет в БД; customer.id, если покупатель присутствует в БД
     */
    private int isCustomerNew(Customer customer) {
        List<Map<String, Object>> list = orderDAO.getAllInfOnCustomers();
        for (Map<String, Object> row : list) {
            if (row.get("EMAIL_ADDRESS").equals(customer.getEmailAddress())) { return (int) row.get("ID"); }
        }
        return 0;
    }

}
