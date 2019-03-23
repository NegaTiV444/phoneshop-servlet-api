package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {

    private ArrayListProductDao productDao = ArrayListProductDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query"), sortBy =  request.getParameter("sortBy"),
                order = request.getParameter("order");
        if ((query != null) && (sortBy != null) && (order != null))
            request.setAttribute("products", productDao.findProducts(query, sortBy, order));
        else
            if (query != null)
                request.setAttribute("products", productDao.findProducts(query));
            else
                request.setAttribute("products", productDao.findProducts());
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
