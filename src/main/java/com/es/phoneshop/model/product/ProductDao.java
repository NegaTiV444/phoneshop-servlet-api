package com.es.phoneshop.model.product;


import java.util.List;

public interface ProductDao {
    Product getProduct(String code) throws IllegalArgumentException;

    Product getProduct(Long id) throws IllegalArgumentException;

    List<Product> findProducts();

    List<Product> findProducts(String query);

    List<Product> findProducts(String query, String sortBy, String order);

    void save(Product product) throws IllegalArgumentException;
    void delete(Long id) throws IllegalArgumentException;
}
