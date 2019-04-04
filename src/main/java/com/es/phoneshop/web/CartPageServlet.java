package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.*;
import com.es.phoneshop.model.exceptions.OutOfStockException;
import com.es.phoneshop.model.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CartPageServlet extends HttpServlet {

    private static final String CART_KEY = "cart";
    private static final String NOT_A_NUMBER_ERROR_MSG = "not.a.number.error";
    private static final String OUT_OF_STOCK_ERROR_MSG = "out.of.stock.error";
    private static final String INVALID_QUANTITY_ERROR_MSG = "invalid.quantity.error";
    private static final String SUCCESSFUL_ADDED_MSG = "added.to.cart";

    private final ArrayListProductDao productDao = ArrayListProductDao.getInstance();

    private final CartService cartService = HttpSessionCartService.newInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        cartService.getCartFromSource(req.getSession());
        req.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getRequestURI().substring(request.getRequestURI().lastIndexOf('/') + 1).equals("cart")) { //update
            update(request);
            doGet(request, response);
        } else { //add
            response.sendRedirect(request.getContextPath() + "/products/" + getAddMsg(request));
        }
    }


    private String getAddMsg(HttpServletRequest request) throws ProductNotFoundException {
        String productCode = request.getRequestURI().substring(request.getRequestURI().lastIndexOf('/') + 1);
        Product product = productDao.getProduct(productCode);
        Cart cart = cartService.getCartFromSource(request.getSession());
        int quantity = 1;
        try {
            quantity = Integer.parseInt(request.getParameter("quantity"));
            if (quantity < 1) {
                return productCode + "?q=" + quantity + "&msg=" + INVALID_QUANTITY_ERROR_MSG;
            } else {
                CartTransaction transaction = cartService.startTransaction(cart);
                transaction.addOrUpdate(product, quantity, false);
                transaction.commit();
            }
            return productCode + "?q=" + quantity + "&msg=" + SUCCESSFUL_ADDED_MSG;
        } catch (NumberFormatException e) {
            return productCode + "?q=" + quantity + "&msg=" + NOT_A_NUMBER_ERROR_MSG;
        } catch (OutOfStockException e) {
            return productCode + "?q=" + quantity + "&msg=" + OUT_OF_STOCK_ERROR_MSG;
        }
    }

    private void update(HttpServletRequest request) {
        Cart cart = cartService.getCartFromSource(request.getSession());
        String[] quantities = request.getParameterValues("newQuantity");
        String[] messages = new String[quantities.length];
        int newQuantity;
        boolean isOk = true;
        CartTransaction transaction = cartService.startTransaction(cart);
        for (int i = 0; i < quantities.length; i++) {
            try {
                newQuantity = Integer.parseInt(quantities[i]);
                if (newQuantity < 1) {
                    messages[i] = INVALID_QUANTITY_ERROR_MSG;
                    isOk = false;
                } else {
                    Product product = cart.getItems().get(i).getProduct();
                    transaction.addOrUpdate(product, newQuantity, true);
                    messages[i] = "success";
                }
            } catch (NumberFormatException e) {
                messages[i] = NOT_A_NUMBER_ERROR_MSG;
                isOk = false;
            } catch (OutOfStockException e) {
                messages[i] = OUT_OF_STOCK_ERROR_MSG;
                isOk = false;
            }
        }
        if (isOk) {
            transaction.commit();
        }
        request.setAttribute("msg", messages);
        request.setAttribute("q", quantities);
    }
}
