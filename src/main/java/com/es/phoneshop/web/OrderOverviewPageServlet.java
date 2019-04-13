package com.es.phoneshop.web;

import com.es.phoneshop.model.order.ArrayListOrderDao;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderOverviewPageServlet extends HttpServlet {

    private final OrderDao orderDao = ArrayListOrderDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Order order = orderDao.getOrder(req.getRequestURI().substring(req.getRequestURI().lastIndexOf('/') + 1));
        req.setAttribute("order", order);
        req.getRequestDispatcher("/WEB-INF/pages/overview.jsp").forward(req, resp);
    }
}
