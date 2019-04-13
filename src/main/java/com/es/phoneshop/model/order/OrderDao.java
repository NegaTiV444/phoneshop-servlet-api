package com.es.phoneshop.model.order;

public interface OrderDao {

    void addOrder(Order order);
    Order getOrder(String UID);
    void deleteOrder(String UID);
}
