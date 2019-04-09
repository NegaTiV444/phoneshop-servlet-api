package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.CartTransaction;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CartItemDeleteServlet extends HttpServlet {

    private final ArrayListProductDao productDao = ArrayListProductDao.getInstance();

    private final CartService cartService = HttpSessionCartService.newInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI().substring(req.getRequestURI().lastIndexOf('/') + 1);
        Product product = productDao.getProduct(uri);
        CartTransaction transaction = cartService.startTransaction(cartService.getCartFromSource(req.getSession()));
        transaction.delete(product);
        transaction.commit();
        resp.sendRedirect(req.getContextPath() + "/cart");
    }
}
