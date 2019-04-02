package com.es.phoneshop.model.history;

import com.es.phoneshop.model.product.Product;

import java.util.Deque;
import java.util.LinkedList;

public class History {

    private Deque<Product> recentProducts = new LinkedList<>();

    public Deque<Product> getRecentProducts() {
        return recentProducts;
    }

}
