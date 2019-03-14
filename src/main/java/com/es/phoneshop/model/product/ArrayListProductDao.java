package com.es.phoneshop.model.product;

import com.es.phoneshop.model.exceptions.ListAlreadyContainsProductException;
import com.es.phoneshop.model.exceptions.ListDoesNotContainProductException;

import java.util.ArrayList;
import java.util.List;


public class ArrayListProductDao implements ProductDao {

    private List<Product> products = new ArrayList<>();

    @Override
    public Product getProduct(Long id) throws ListDoesNotContainProductException {
        if (products.stream().anyMatch(p -> p.getId().equals(id)))
            return products.stream().filter(product -> product.getId().equals(id)).findAny().get();
        else
            throw new ListDoesNotContainProductException("List doesn't contains product " + id);
    }

    @Override
    public List<Product> findProducts() {
        return products;
    }

    @Override
    public void save(Product product) throws ListAlreadyContainsProductException {
        if (products.stream().anyMatch(p -> p.getId().equals(product.getId())))
            throw new ListAlreadyContainsProductException("List already contains product " + product.getId());
        else
            products.add(product);
    }

    @Override
    public void delete(Long id) throws ListDoesNotContainProductException {
        if (products.stream().anyMatch(p -> p.getId().equals(id)))
            products.remove(products.stream().filter(product -> product.getId().equals(id)).findAny().get());
        else
            throw new ListDoesNotContainProductException("List doesn't contains product " + id);
    }
}
