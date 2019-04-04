package com.es.phoneshop.model.cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<CartItem> items = new ArrayList<>();

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
        recalculateTotalPrice();
    }

    private BigDecimal totalPrice = BigDecimal.ZERO;

    public long getTotalPrice() {
        return totalPrice.longValue();
    }

    public void recalculateTotalPrice(){
        totalPrice = BigDecimal.ZERO;
        items.stream()
                .filter(cartItem ->   null != cartItem.getProduct().getPrice())
                .forEach(cartItem -> totalPrice = totalPrice.add(cartItem.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity()))));
    }

}
