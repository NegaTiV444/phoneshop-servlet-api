package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ArrayListProductDaoTest
{
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void testFindProductsNoResults() {
        assertTrue(productDao.findProducts().isEmpty());
    }

    @Mock
    private Product product;

    @Test
    public void testGetProductWithIncorrectId()
    {
        boolean isException = false;
        try {
            productDao.getProduct(product.getId());
        } catch (IllegalArgumentException e) {
            isException = true;
        }
        assertTrue(isException);
    }

    @Test
    public void testSaveOneProductTwice()
    {
        boolean isException = false;
        try {
            productDao.save(product);
            productDao.save(product);
        } catch (IllegalArgumentException e) {
            isException = true;
        }
        assertTrue(isException);
    }

    @Test
    public void testSaveAndGet()
    {
        try {
            productDao.save(product);
            assertEquals(product, productDao.getProduct(product.getId()));
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDelete()
    {
        boolean isException = false;
        try {
            productDao.save(product);
            productDao.delete(product.getId());
        } catch (IllegalArgumentException e) {
            isException = true;
        }
        assertTrue(!isException);
    }
}
