package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.exceptions.OutOfStockException;
import com.es.phoneshop.model.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;

public interface CartService {

    Cart getCartFromSource(Object src);

    CartTransaction startTransaction(Cart cart);
}
