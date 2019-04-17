package com.es.phoneshop.model.productReview;

import com.es.phoneshop.model.product.Product;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ArrayListProductReviewDaoTest {

    private static ProductReviewDao productReviewDao;
    private static ProductReview review;
    private static Product product;


    @BeforeClass
    public static void init() {
        productReviewDao = ArrayListProductReviewDao.getInstance();
        review = new ProductReview();

        product = new Product();
        product.setId(6666L);
        product.setPrice(new BigDecimal(10000));
        product.setStock(1);
        product.setCode("iphoneXX");
        product.setDescription("Iphone XX");

        review.setProduct(product);
        review.setName("AppleFan");
        review.setComment("Great. Best display, best camera, i absolutely lobe it!");
        review.setRating(5);
        productReviewDao.addReview(review);

    }

    @Test
    public void testGet(){
        assertEquals(review, productReviewDao.getReview(review.getUid()));
    }

    @Test
    public void testAdd(){
        ProductReview newReview = new ProductReview();

        newReview.setProduct(product);
        newReview.setName("SamsungFan");
        newReview.setComment("Shit. Don't buy it please!");
        newReview.setRating(1);

        productReviewDao.addReview(newReview);
        assertEquals(newReview, productReviewDao.getReview(newReview.getUid()));
    }
}
