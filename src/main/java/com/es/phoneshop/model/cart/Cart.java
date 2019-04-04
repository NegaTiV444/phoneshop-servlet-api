package com.es.phoneshop.model.cart;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<CartItem> items = new ArrayList<>();

    public List<CartItem> getItems() {
        return items;
    }

    private BigDecimal totalPrice;

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
