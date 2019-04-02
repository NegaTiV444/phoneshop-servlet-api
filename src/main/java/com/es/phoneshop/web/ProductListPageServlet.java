package com.es.phoneshop.web;

import com.es.phoneshop.model.history.HistoryService;
import com.es.phoneshop.model.history.HttpSessionHistoryService;
import com.es.phoneshop.model.product.ArrayListProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {

    private final ArrayListProductDao productDao = ArrayListProductDao.getInstance();
    private final HistoryService historyService = HttpSessionHistoryService.newInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        String sortBy = request.getParameter("sortBy");
        String order = request.getParameter("order");
        //Put an attribute 'history' in session, because recentlyViewed.tag in productList.jsp uses bean 'history'
        //historyService.getHistoryFromSource(request.getSession());
        request.setAttribute("history", historyService.getHistoryFromSource(request.getSession()));
        request.setAttribute("products", productDao.findProducts(query, sortBy, order));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

}
