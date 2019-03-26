package com.es.phoneshop.model.product;


import java.util.List;

public interface ProductDao {
    Product getProduct(String code);

    Product getProduct(Long id);

    List<Product> findProducts();

    List<Product> findProducts(String query);

    List<Product> findProducts(String query, String sortBy, String order);

    void save(Product product);

    void delete(Long id);
}
