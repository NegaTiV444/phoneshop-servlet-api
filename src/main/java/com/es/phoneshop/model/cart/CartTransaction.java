package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CartTransaction {

    private List<CartItem> items = new ArrayList<>();
    private Cart cart;

    public List<CartItem> getItems() {
        return items;
    }

    CartTransaction(Cart cart){
        this.cart = cart;
        for (CartItem cartItem : cart.getItems()){
            items.add(new CartItem(cartItem.getProduct(), cartItem.getQuantity()));
        }
    }

    public void addOrUpdate(Product product, int quantity, boolean isUpdate) throws OutOfStockException {
        Optional<CartItem> cartItemOptional = items.stream()
                .filter(cartItem1 -> cartItem1.getProduct().equals(product))
                .findAny();
        int newQuantity;
        newQuantity = isUpdate ? quantity : cartItemOptional.map(CartItem::getQuantity).orElse(0) + quantity;
        if (product.getStock() < newQuantity) {
            throw new OutOfStockException();
        } else if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            if (newQuantity == 0) {
                items.remove(cartItem);
            } else {
                cartItem.setQuantity(newQuantity);
            }
        } else {
            items.add(new CartItem(product, quantity));
        }
    }

    public void commit() {
        cart.getItems().clear();
        cart.getItems().addAll(items);
        cart.recalculateTotalPrice();
    }
}
