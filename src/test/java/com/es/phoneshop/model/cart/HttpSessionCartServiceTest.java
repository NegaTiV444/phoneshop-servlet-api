package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {

    private static final String CART_KEY = "cart";

    private static CartService cartService;
    private static Product product;

    @Mock
    private HttpSession session;

    @Mock
    private Cart mockCart;

    @BeforeClass
    public static void setup() {
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
        CartTransaction transaction = cartService.startTransaction(cart);
        transaction.addOrUpdate(product, 2, false);
        transaction.commit();
        assertEquals(cart.getItems().get(0).getProduct(), product);
    }

    @Test(expected = OutOfStockException.class)
    public void addToCartOutOfStockTest() throws OutOfStockException {
        Cart cart = new Cart();
        CartTransaction transaction = cartService.startTransaction(cart);
        transaction.addOrUpdate(product, 4, false);
        transaction.commit();
    }

    @Test
    public void getCartFromSource() {
        Cart newCart = cartService.getCartFromSource(session);
        verify(session).setAttribute(CART_KEY, newCart);
        assertNotNull(newCart);
    }

    @Test
    public void getExistingCartFromSource() {
        when(session.getAttribute(CART_KEY)).thenReturn(mockCart);
        Cart result = cartService.getCartFromSource(session);
        assertEquals(mockCart, result);
        verify(session, never()).setAttribute(anyString(), Mockito.any(Cart.class));

    }

}
