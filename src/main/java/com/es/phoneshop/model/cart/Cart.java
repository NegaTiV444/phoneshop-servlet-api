package com.es.phoneshop.model.cart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {

    private List<CartItem> items = new ArrayList<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;

    public long getTotalProducts() {
        return totalProducts;
    }

    private long totalProducts = 0;

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
        recalculateTotalPrice();
    }

    public long getTotalPrice() {
        return totalPrice.longValue();
    }

    public void recalculateTotalPrice() {
        totalPrice = BigDecimal.ZERO;
        items.stream()
                .filter(cartItem -> null != cartItem.getProduct().getPrice())
                .forEach(cartItem -> totalPrice = totalPrice.add(cartItem.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity()))));
        totalProducts = 0;
        items.stream()
                .filter(cartItem -> null != cartItem.getProduct().getPrice())
                .forEach(cartItem -> totalProducts = totalProducts + cartItem.getQuantity());
    }

}
