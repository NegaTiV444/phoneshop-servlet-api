package com.es.phoneshop.model.order;

import com.es.phoneshop.model.exceptions.OrderNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArrayListOrderDao implements OrderDao {

    private List<Order> orders = new ArrayList<>();

    private ArrayListOrderDao(){}

    private static class SingletonHandler{
        static final ArrayListOrderDao INSTANCE = new ArrayListOrderDao();
    }

    public static ArrayListOrderDao getInstance(){
        return SingletonHandler.INSTANCE;
    }

    @Override
    public void addOrder(Order order) {
        orders.add(order);
    }

    @Override
    public Order getOrder(String UID) {
        return orders.stream()
                .filter(order -> order.getUID().equals(UID))
                .findAny()
                .orElseThrow(() -> new OrderNotFoundException("Order with UID " + UID + " not found"));
    }

    @Override
    public void deleteOrder(String UID) {
        Optional<Order> orderOptional = orders.stream()
                .filter(order -> order.getUID().equals(UID))
                .findAny();
        if (orderOptional.isPresent()){
            orders.remove(orderOptional.get());
        } else {
            throw new OrderNotFoundException("Order with UID " + UID + " not found");
        }
    }
}
