package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

import static junit.framework.TestCase.*;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {

    private static final String CART_KEY = "cart";

    private static CartService cartService;
    private static Product product;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @BeforeClass
    public static void init(){
        cartService = HttpSessionCartService.newInstance();
        product = new Product();
        product.setId(6666L);
        product.setPrice(new BigDecimal(100));
        product.setStock(3);
        product.setCode("iphoneXX");
        product.setDescription("Iphone XX");
    }

    @Test
    public void addToCartTest() throws OutOfStockException {
        Cart cart = new Cart();
        cartService.addToCart(cart, product, 2);
        assertTrue(cart.getItems().get(0).getProduct().equals(product));
    }

    @Test(expected = OutOfStockException.class)
    public void addToCartOutOfStockTest() throws OutOfStockException {
        Cart cart = new Cart();
        cartService.addToCart(cart, product,  4);
    }

    @Test
    public void getCartFromSource() throws OutOfStockException {
        Cart newCart = cartService.getCartFromSource(session);
        assertNotNull(newCart);
    }
}
