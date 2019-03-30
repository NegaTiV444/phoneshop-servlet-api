package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {
    private final String CART_KEY = "cart";
    private final String NOT_A_NUMBER_ERROR_MSG = "nan"; //Not a number
    private final String OUT_OF_STOCK_ERROR_MSG = "oos"; //Not enough products in stock
    private final String INVALID_QUNTITY_ERROR_MSG = "iq"; //Quantity must be greater than 0
    private final String SUCCESSFUL_ADDED_MSG = "atc"; //Added to cart



    private final ArrayListProductDao productDao = ArrayListProductDao.getInstance();
    private final CartService cartService = HttpSessionCartService.newInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = productDao.getProduct(request.getParameter("code"));
        request.setAttribute("product", product);
        request.setAttribute("stock", product.getStock());
        request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Product product = productDao.getProduct(req.getParameter("code"));
        Cart cart = (Cart) session.getAttribute(CART_KEY);
        if (cart == null) {
            cart = new Cart();
            session.setAttribute(CART_KEY, cart);
        }
        int quantity = 1;
        String msg;
        try {
            quantity = Integer.parseInt(req.getParameter("quantity"));
            if (quantity < 1) {
                msg = INVALID_QUNTITY_ERROR_MSG;
            } else {
                cartService.addToCart(cart, product, quantity);
                msg = SUCCESSFUL_ADDED_MSG;
            }
        } catch (NumberFormatException e) {
            msg = NOT_A_NUMBER_ERROR_MSG;
        } catch (OutOfStockException e) {
            msg = OUT_OF_STOCK_ERROR_MSG;
        }
        resp.sendRedirect(req.getRequestURI() + "?code="+ req.getParameter("code") + "&q=" + quantity + "&msg=" + msg);

    }

}
