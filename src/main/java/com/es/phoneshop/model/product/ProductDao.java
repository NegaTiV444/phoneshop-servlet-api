package com.es.phoneshop.model.product;


import com.es.phoneshop.model.exceptions.ProductNotFoundException;

import java.util.List;

public interface ProductDao {
    Product getProduct(String code) throws ProductNotFoundException;

    Product getProduct(Long id) throws ProductNotFoundException;

    List<Product> findProducts();

    List<Product> findProducts(String query);

    List<Product> findProducts(String query, String sortBy, String order);

    void save(Product product);

    void delete(Long id) throws ProductNotFoundException;
}
