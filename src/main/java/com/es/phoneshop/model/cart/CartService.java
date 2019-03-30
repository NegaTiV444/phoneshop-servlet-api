package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;

public interface CartService {

    void addToCart(Cart cart, Product product, int quantity) throws OutOfStockException;

    Cart getCartFromSource(Object src);
}
