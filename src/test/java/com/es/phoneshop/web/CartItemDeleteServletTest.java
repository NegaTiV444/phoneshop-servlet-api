package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartItemDeleteServletTest {

    private static Cart cart = new Cart();
    private static ProductDao productDao;
    private static Product product1;
    private static Product product2;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    private CartItemDeleteServlet servlet = new CartItemDeleteServlet();

    @BeforeClass
    public static void init() {

        productDao = ArrayListProductDao.getInstance();
        Currency usd = Currency.getInstance("USD");
        product1 = new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product1);
        product2 = new Product(4L, "iphone", "Apple iPhone", new BigDecimal(200), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg");
        productDao.save(product2);
        cart.getItems().add(new CartItem(product1, 2));
        cart.getItems().add(new CartItem(product2, 1));
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        when(request.getContextPath()).thenReturn("contextPath");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("cart")).thenReturn(cart);
        when(request.getRequestURI()).thenReturn("/cart/delete/sgs");
        servlet.doPost(request, response);
        assertEquals(cart.getItems().size(), 1);
    }

}
