package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.exceptions.OutOfStockException;
import com.es.phoneshop.model.history.History;
import com.es.phoneshop.model.history.HistoryService;
import com.es.phoneshop.model.history.HttpSessionHistoryService;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {
    private static final String CART_KEY = "cart";
    private static final String NOT_A_NUMBER_ERROR_MSG = "not.a.number.error";
    private static final String OUT_OF_STOCK_ERROR_MSG = "out.of.stock.error";
    private static final String INVALID_QUANTITY_ERROR_MSG = "invalid.quantity.error";
    private static final String SUCCESSFUL_ADDED_MSG = "added.to.cart";


    private final ArrayListProductDao productDao = ArrayListProductDao.getInstance();
    private final CartService cartService = HttpSessionCartService.newInstance();
    private final HistoryService historyService = HttpSessionHistoryService.newInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = productDao.getProduct(request.getRequestURI().substring(request.getRequestURI().lastIndexOf('/') + 1));
        request.setAttribute("product", product);
        updateHistory(request, product);
        request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
    }

    private void updateHistory(HttpServletRequest req, Product product) {
        HttpSession session = req.getSession();
        History history = historyService.getHistoryFromSource(session);
        if (history == null) {
            history = new History();
        }
        historyService.put(history, product);
    }

}
