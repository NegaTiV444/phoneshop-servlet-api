package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.enums.DeliveryMethod;
import com.es.phoneshop.model.enums.PaymentMethod;
import com.es.phoneshop.model.order.HttpSessionOrderService;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CheckoutPageServlet extends HttpServlet {

    private static final String ORDER_KEY = "order";

    private final CartService cartService = HttpSessionCartService.getInstance();

    private final OrderService orderService = HttpSessionOrderService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Cart cart = cartService.getCartFromSource(session);
        orderService.getOrderFromSource(session, cart);
        req.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String isUpdate = req.getParameter("isUpdate");
        HttpSession session = req.getSession();
        Order order = orderService.getOrderFromSource(session, cartService.getCartFromSource(session));
        order.setFirstName(req.getParameter("firstName"));
        order.setLastName(req.getParameter("lastName"));
        order.setPhone(req.getParameter("phone"));
        order.setAddress(req.getParameter("address"));
        order.setDeliveryMethod(DeliveryMethod.valueOf(req.getParameter("deliveryMethod")));
        order.setPaymentMethod(PaymentMethod.valueOf(req.getParameter("paymentMethod")));
        if (isUpdate.equals("true")) {
            resp.sendRedirect("checkout");
        } else {
            orderService.placeOrder(order);
            session.removeAttribute(ORDER_KEY);
            resp.sendRedirect("order/overview/" + order.getUID());
        }
    }
}
