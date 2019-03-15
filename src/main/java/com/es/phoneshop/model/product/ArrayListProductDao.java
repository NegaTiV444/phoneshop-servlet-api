package com.es.phoneshop.model.product;


import java.util.ArrayList;
import java.util.List;


public class ArrayListProductDao implements ProductDao {

    private List<Product> products = new ArrayList<>();

    @Override
    public Product getProduct(Long id) throws IllegalArgumentException{
        return products.stream().filter(product -> product.getId().equals(id)).findAny().orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<Product> findProducts() {
        return products;
    }

    @Override
    public void save(Product product) throws IllegalArgumentException {
        if (products.stream().anyMatch(p -> p.getId().equals(product.getId())))
            throw new IllegalArgumentException();
        else
            products.add(product);
    }

    @Override
    public void delete(Long id) throws IllegalArgumentException {
            products.remove(products.stream().filter(product -> product.getId().equals(id)).findAny().orElseThrow(IllegalArgumentException::new));
    }
}
