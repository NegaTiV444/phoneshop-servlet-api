package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;


import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ArrayListProductDaoTest
{
    private ProductDao productDao;
    private Product product;

    @Before
    public void setup() {
        productDao = ArrayListProductDao.getInstance();
        product = new Product();
        product.setId(666L);
        product.setPrice(new BigDecimal(100));
        product.setStock(1);
    }

    @Test
    public void testFindProductsNoResults() {
        assertTrue(productDao.findProducts().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetProductWithIncorrectId()
    {
        Product product = new Product();
        productDao.getProduct(product.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveOneProductTwice()
    {
        productDao.save(product);
        productDao.save(product);
    }

    @Test
    public void testSave()
    {
        productDao.save(product);
        assertFalse(productDao.findProducts().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDelete()
    {
        productDao.save(product);
        productDao.delete(product.getId());
        productDao.getProduct(product.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteWithIncorrectId()
    {
        productDao.delete(product.getId());
    }
}
