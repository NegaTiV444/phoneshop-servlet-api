package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.productReview.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ReviewModerationPageServlet extends HttpServlet {

    private final ProductReviewService productReviewService = ProductReviewServiceImpl.getInstance();
    private final ProductReviewDao productReviewDao = ArrayListProductReviewDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ProductReview> reviews = productReviewDao.findReviews();
        req.setAttribute("reviews", reviews);
        req.getRequestDispatcher("/WEB-INF/pages/reviewsModeration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uid = req.getRequestURI().substring(req.getRequestURI().lastIndexOf('/') + 1);
        ProductReview review = productReviewDao.getReview(uid);
        productReviewService.approveReview(review);
        resp.sendRedirect("moderation");
    }
}
