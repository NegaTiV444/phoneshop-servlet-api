package com.es.phoneshop.model.product;

import com.es.phoneshop.model.exceptions.ProductNotFoundException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ArrayListProductDaoTest {
    private static ProductDao productDao;
    private static Product product;
    private static List<Product> validProductList;

    @BeforeClass
    public static void setupClass(){
        productDao = ArrayListProductDao.getInstance();
        product = new Product();
        product.setId(6666L);
        product.setPrice(new BigDecimal(100));
        product.setStock(1);
        product.setCode("iphoneXX");
        product.setDescription("Iphone XX");

        validProductList = new ArrayList<>();

        Product p1, p2, p3, p4;
        p1 = new Product();
        p1.setStock(1);
        p1.setId(1L);
        p1.setCode("aphone1");
        p1.setPrice(new BigDecimal(100));
        p1.setDescription("APhone 1");

        p2 = new Product();
        p2.setStock(1);
        p2.setId(2L);
        p2.setCode("bphone1");
        p2.setPrice(new BigDecimal(200));
        p2.setDescription("BPhone 1");

        p3 = new Product();
        p3.setStock(1);
        p3.setId(3L);
        p3.setCode("cphone2");
        p3.setPrice(new BigDecimal(1000));
        p3.setDescription("CPhone 2");

        p4 = new Product();
        p4.setStock(1);
        p4.setId(4L);
        p4.setCode("cphone1");
        p4.setPrice(new BigDecimal(400));
        p4.setDescription("CPhone 1");

        validProductList.add(p1);
        validProductList.add(p2);
        validProductList.add(p3);
        validProductList.add(p4);

        productDao.save(p1);
        productDao.save(p2);
        productDao.save(p3);
        productDao.save(p4);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testGetProductWithIncorrectId() throws ProductNotFoundException {
        productDao.getProduct(333L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveOneProductTwice() throws ProductNotFoundException {
        productDao.save(product);
        productDao.save(product);
        productDao.delete(product.getId());
    }

    @Test
    public void testSave() throws ProductNotFoundException {
        productDao.save(product);
        Product kek = productDao.getProduct(product.getId());
        assertEquals(product, kek);
        productDao.delete(product.getId());
    }

    @Test(expected = ProductNotFoundException.class)
    public void testDelete() throws ProductNotFoundException {
        Product product1 = new Product();
        product1.setId(6665L);
        product1.setPrice(new BigDecimal(1001));
        product1.setStock(2);
        product1.setCode("zphoneXXXX");
        product1.setDescription("Zphone XXXX");

        productDao.save(product1);
        productDao.delete(product1.getId());
        productDao.getProduct(product1.getId());
    }

    @Test(expected = ProductNotFoundException.class)
    public void testDeleteWithIncorrectId() throws ProductNotFoundException {
        productDao.delete(product.getId());
    }

    //Lecture 2

    @Test
    public void sortingTest(){

        List<Product> products = productDao.findProducts(null, "description", "asc");
        assertEquals(validProductList.get(0), products.get(0));
        assertEquals(validProductList.get(1), products.get(1));
        assertEquals(validProductList.get(2), products.get(3));
        assertEquals(validProductList.get(3), products.get(2));

        products = productDao.findProducts(null, "price", "desc");
        assertEquals(validProductList.get(0), products.get(3));
        assertEquals(validProductList.get(1), products.get(2));
        assertEquals(validProductList.get(2), products.get(0));
        assertEquals(validProductList.get(3), products.get(1));
    }

    @Test
    public void searchingTest(){

        List<Product> products = productDao.findProducts("B");
        assertEquals(1, products.size());
        assertEquals(validProductList.get(1), products.get(0));

        products = productDao.findProducts("A 1");
        assertEquals(3, products.size());
        assertEquals(validProductList.get(0), products.get(0));
    }

    @Test
    public void searchingAndSortingTest(){

        List<Product> products = productDao.findProducts("1", "price", "asc");
        assertEquals(3, products.size());
        assertEquals(validProductList.get(0), products.get(0));
        assertEquals(validProductList.get(1), products.get(1));
        assertEquals(validProductList.get(3), products.get(2));
    }
}
