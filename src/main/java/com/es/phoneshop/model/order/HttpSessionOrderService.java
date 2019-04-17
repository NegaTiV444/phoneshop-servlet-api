package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.CartTransaction;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.enums.DeliveryMethod;
import com.es.phoneshop.model.enums.PaymentMethod;

import javax.servlet.http.HttpSession;
import java.util.UUID;

public class HttpSessionOrderService implements OrderService {

    private static final String ORDER_KEY = "order";

    private final CartService cartService = HttpSessionCartService.getInstance();

    private ArrayListOrderDao orderDao = ArrayListOrderDao.getInstance();

    private HttpSessionOrderService() {
    }

    public static HttpSessionOrderService getInstance() {
        return SingletonHandler.INSTANCE;
    }

    @Override
    public Order getOrderFromSource(Object src, Cart cart) {
        Order order;
        HttpSession session;
        try {
            session = (HttpSession) src;
            order = (Order) session.getAttribute(ORDER_KEY);
            if (order == null) {
                order = new Order();
                order.setCart(cart);
                order.setPaymentMethod(PaymentMethod.CASH);
                order.setDeliveryMethod(DeliveryMethod.STOREPICKUP);
                order.setAddress("");
                order.setPhone("");
                order.setLastName("");
                order.setFirstName("");
                session.setAttribute(ORDER_KEY, order);
            }
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Source must be HttpSession");
        }
        return order;
    }

    @Override
    public void placeOrder(Order order) {
        order.setUID(UUID.randomUUID().toString());
        orderDao.addOrder(order);
        CartTransaction transaction = cartService.startTransaction(order.getCart());
        order.getItems().forEach(cartItem -> transaction.delete(cartItem.getProduct()));
        transaction.commit();
    }

    private static class SingletonHandler {
        static final HttpSessionOrderService INSTANCE = new HttpSessionOrderService();
    }
}
