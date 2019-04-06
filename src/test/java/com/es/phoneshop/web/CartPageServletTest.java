package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.Before;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartPageServletTest {

    private static final String NOT_A_NUMBER_ERROR_MSG = "not.a.number.error";
    private static final String OUT_OF_STOCK_ERROR_MSG = "out.of.stock.error";
    private static final String INVALID_QUANTITY_ERROR_MSG = "invalid.quantity.error";
    private static final String SUCCESSFUL_ADDED_MSG = "added.to.cart";
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
    private CartPageServlet servlet = new CartPageServlet();

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

    @Before
    public void setup() {
        when(request.getContextPath()).thenReturn("contextPath");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("cart")).thenReturn(cart);
    }

    @Test
    public void testDoPostUpdateWithErrors() throws IOException, ServletException {
        cart.getItems().get(0).setQuantity(2);
        cart.getItems().get(1).setQuantity(1);
        when(request.getRequestURI()).thenReturn("/cart");
        String[] newMsg = {OUT_OF_STOCK_ERROR_MSG, OUT_OF_STOCK_ERROR_MSG};
        String[] newQ = {"300", "50"};
        when(request.getParameterValues("newQuantity")).thenReturn(newQ);
        when(request.getAttribute("msg")).thenReturn(newMsg);
        when(request.getAttribute("q")).thenReturn(newQ);

        servlet.doPost(request, response);

        verify(session).setAttribute("msg", request.getAttribute("msg"));
        verify(session).setAttribute("q", request.getAttribute("q"));
        assertEquals(cart.getItems().get(0).getQuantity(), 2);
        assertEquals(cart.getItems().get(1).getQuantity(), 1);
    }

    @Test
    public void testDoPostAddSuccess() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("/cart/sgs");
        when(request.getParameter("quantity")).thenReturn("1");
        servlet.doPost(request, response);
        verify(response).sendRedirect("contextPath" + "/products/" + "sgs" + "?q=" + 1 + "&msg=" + SUCCESSFUL_ADDED_MSG);
    }

    @Test
    public void testDoPostAddOutOfStock() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("/cart/sgs");
        when(request.getParameter("quantity")).thenReturn("15");
        servlet.doPost(request, response);
        verify(response).sendRedirect("contextPath" + "/products/" + "sgs" + "?q=" + 15 + "&msg=" + OUT_OF_STOCK_ERROR_MSG);
    }

    @Test
    public void testDoPostAddNotANumber() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("/cart/sgs");
        when(request.getParameter("quantity")).thenReturn("1e");
        servlet.doPost(request, response);
        verify(response).sendRedirect("contextPath" + "/products/" + "sgs" + "?q=" + "1e" + "&msg=" + NOT_A_NUMBER_ERROR_MSG);
    }

    @Test
    public void testDoPostAddInvalidQuanity() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("/cart/sgs");
        when(request.getParameter("quantity")).thenReturn("-1");
        servlet.doPost(request, response);
        verify(response).sendRedirect("contextPath" + "/products/" + "sgs" + "?q=" + "-1" + "&msg=" + INVALID_QUANTITY_ERROR_MSG);
    }

    @Test
    public void testDoPostUpdate() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("/cart");
        String[] newMsg = {SUCCESSFUL_ADDED_MSG, SUCCESSFUL_ADDED_MSG};
        String[] newQ = {"3", "5"};
        when(request.getParameterValues("newQuantity")).thenReturn(newQ);
        when(request.getAttribute("msg")).thenReturn(newMsg);
        when(request.getAttribute("q")).thenReturn(newQ);

        servlet.doPost(request, response);

        verify(session).setAttribute("msg", request.getAttribute("msg"));
        verify(session).setAttribute("q", request.getAttribute("q"));
        assertEquals(cart.getItems().get(0).getQuantity(), 3);
        assertEquals(cart.getItems().get(1).getQuantity(), 5);
    }

}
