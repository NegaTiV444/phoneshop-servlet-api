package com.es.phoneshop.model.product;

import com.es.phoneshop.model.exceptions.ListAlreadyContainsProductException;
import com.es.phoneshop.model.exceptions.ListDoesNotContainProductException;

import java.util.List;

public interface ProductDao {
    Product getProduct(Long id) throws IllegalArgumentException;
    List<Product> findProducts();
    void save(Product product) throws IllegalArgumentException;
    void delete(Long id) throws IllegalArgumentException;
}
