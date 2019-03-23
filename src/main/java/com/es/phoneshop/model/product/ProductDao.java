package com.es.phoneshop.model.product;


import java.util.List;

public interface ProductDao {
    Product getProduct(String code) throws IllegalArgumentException;

    Product getProduct(Long id) throws IllegalArgumentException;
    List<Product> findProducts();

    List<Product> findProducts(String query);

    void save(Product product) throws IllegalArgumentException;
    void delete(Long id) throws IllegalArgumentException;
}
