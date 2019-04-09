package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.exceptions.OutOfStockException;
import com.es.phoneshop.model.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CartTransaction {

    private List<CartItem> items = new ArrayList<>();
    private Cart cart;

    CartTransaction(Cart cart) {
        this.cart = cart;
        for (CartItem cartItem : cart.getItems()) {
            items.add(new CartItem(cartItem.getProduct(), cartItem.getQuantity()));
        }
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void add(Product product, int quantity) throws OutOfStockException {
        Optional<CartItem> cartItemOptional = items.stream()
                .filter(cartItem1 -> cartItem1.getProduct().equals(product))
                .findAny();
        int newQuantity = cartItemOptional.map(CartItem::getQuantity).orElse(0) + quantity;
        if (product.getStock() < newQuantity) {
            throw new OutOfStockException();
        } else if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            cartItem.setQuantity(newQuantity);
        } else {
            items.add(new CartItem(product, quantity));
        }
    }

    public void update(Product product, int newQuantity) throws OutOfStockException, ProductNotFoundException {
        Optional<CartItem> cartItemOptional = items.stream()
                .filter(cartItem1 -> cartItem1.getProduct().equals(product))
                .findAny();
        if (product.getStock() < newQuantity) {
            throw new OutOfStockException();
        } else if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            cartItem.setQuantity(newQuantity);
        } else {
            throw new ProductNotFoundException("Product " + product.getCode() + " not found in the cart");
        }
    }

    public void delete(Product product) throws ProductNotFoundException {
        CartItem cartItem = items.stream()
                .filter(cartItem1 -> cartItem1.getProduct().equals(product))
                .findAny().orElseThrow(() -> new ProductNotFoundException("Product with code " + product.getCode() + " not found in cart"));
        items.remove(cartItem);
    }

    private void recalculateTotalPrice() {
        final BigDecimal[] totalPrice = {BigDecimal.ZERO};
        items.stream()
                .filter(cartItem -> null != cartItem.getProduct().getPrice())
                .forEach(cartItem -> totalPrice[0] = totalPrice[0].add(cartItem.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity()))));
        cart.setTotalPrice(totalPrice[0]);
        long newTotalProducts = 0;
        newTotalProducts += items.stream()
                .filter(cartItem -> null != cartItem.getProduct().getPrice()).mapToLong(CartItem::getQuantity).sum();
        cart.setTotalProducts(newTotalProducts);
    }

    public void commit() {
        cart.getItems().clear();
        cart.getItems().addAll(items);
        recalculateTotalPrice();
    }
}
