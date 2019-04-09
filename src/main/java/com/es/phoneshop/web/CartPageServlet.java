package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.CartTransaction;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.exceptions.OutOfStockException;
import com.es.phoneshop.model.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

public class CartPageServlet extends HttpServlet {

    private static final String NOT_A_NUMBER_ERROR_MSG = "not.a.number.error";
    private static final String OUT_OF_STOCK_ERROR_MSG = "out.of.stock.error";
    private static final String INVALID_QUANTITY_ERROR_MSG = "invalid.quantity.error";
    private static final String SUCCESSFULLY_ADDED_MSG = "added.to.cart";
    private static final String SUCCESSFULLY_UPDATED_MSG = "updated";


    private final ArrayListProductDao productDao = ArrayListProductDao.getInstance();

    private final CartService cartService = HttpSessionCartService.newInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        cartService.getCartFromSource(req.getSession());
        if (null != req.getSession().getAttribute("msg")) {
            req.setAttribute("msg", req.getSession().getAttribute("msg"));
            req.getSession().removeAttribute("msg");
        }
        if (null != req.getSession().getAttribute("q")) {
            req.setAttribute("q", req.getSession().getAttribute("q"));
            req.getSession().removeAttribute("q");
        }
        req.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getRequestURI().substring(request.getRequestURI().lastIndexOf('/') + 1).equals("cart")) { //update
            update(request);
            HttpSession session = request.getSession();
            session.setAttribute("msg", request.getAttribute("msg"));
            session.setAttribute("q", request.getAttribute("q"));
            response.sendRedirect(request.getContextPath() + "/cart");
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
                transaction.add(product, quantity);
                transaction.commit();
            }
            return productCode + "?q=" + quantity + "&msg=" + SUCCESSFULLY_ADDED_MSG;
        } catch (NumberFormatException e) {
            return productCode + "?q=" + request.getParameter("quantity") + "&msg=" + NOT_A_NUMBER_ERROR_MSG;
        } catch (OutOfStockException e) {
            return productCode + "?q=" + quantity + "&msg=" + OUT_OF_STOCK_ERROR_MSG;
        }
    }

    private void isCorrectQuantity(String[] quantities, String[] messages, CartTransaction transaction, Cart cart, int index) {
        try {
            int newQuantity = Integer.parseInt(quantities[index]);
            if (newQuantity < 1) {
                messages[index] = INVALID_QUANTITY_ERROR_MSG;
            } else {
                Product product = cart.getItems().get(index).getProduct();
                transaction.update(product, newQuantity);
                messages[index] = SUCCESSFULLY_UPDATED_MSG;
            }
        } catch (NumberFormatException e) {
            messages[index] = NOT_A_NUMBER_ERROR_MSG;
        } catch (OutOfStockException e) {
            messages[index] = OUT_OF_STOCK_ERROR_MSG;
        }
    }

    private void update(HttpServletRequest request) {
        Cart cart = cartService.getCartFromSource(request.getSession());
        String[] quantities = request.getParameterValues("newQuantity");
        if (quantities != null) {
            String[] messages = new String[quantities.length];
            boolean isOk = false;
            CartTransaction transaction = cartService.startTransaction(cart);
            try {
                Stream
                        .iterate(0, i -> i + 1)
                        .limit(quantities.length)
                        .forEachOrdered(i -> isCorrectQuantity(quantities, messages, transaction, cart, i));
                isOk = Arrays.stream(messages)
                        .allMatch(SUCCESSFULLY_UPDATED_MSG::equals);
            } finally {
                if (isOk) {
                    transaction.commit();
                }
                request.setAttribute("msg", messages);
                request.setAttribute("q", quantities);
            }
        }
    }
}