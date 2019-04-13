package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;

public interface OrderService {

    void placeOrder(Order order);
    Order getOrderFromSource(Object src, Cart cart);

}
