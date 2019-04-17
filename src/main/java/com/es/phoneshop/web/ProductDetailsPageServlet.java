package com.es.phoneshop.web;

import com.es.phoneshop.model.history.History;
import com.es.phoneshop.model.history.HistoryService;
import com.es.phoneshop.model.history.HttpSessionHistoryService;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.productReview.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ProductDetailsPageServlet extends HttpServlet {

    private final ArrayListProductDao productDao = ArrayListProductDao.getInstance();
    private final HistoryService historyService = HttpSessionHistoryService.newInstance();
    private final ProductReviewService productReviewService = ProductReviewServiceImpl.getInstance();
    private final ProductReviewDao productReviewDao = ArrayListProductReviewDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = productDao.getProduct(request.getRequestURI().substring(request.getRequestURI().lastIndexOf('/') + 1));
        request.setAttribute("product", product);
        updateHistory(request, product);

        List<ProductReview> reviews = productReviewDao.findReviews(product);
        request.setAttribute("reviews", reviews);
        request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
    }

    //doPost - add productReview

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Product product = productDao.getProduct(req.getRequestURI().substring(req.getRequestURI().lastIndexOf('/') + 1));
        String name = req.getParameter("name");
        int rating = Integer.parseInt(req.getParameter("rating"));
        String comment = req.getParameter("comment");
        productReviewService.addReview(name, rating, comment, product);
        resp.sendRedirect("products/" + product.getCode());
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
