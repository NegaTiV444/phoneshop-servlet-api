package com.es.phoneshop.model.cart;

import javax.servlet.http.HttpSession;

public class HttpSessionCartService implements CartService {

    private static final String CART_KEY = "cart";

    private HttpSessionCartService() {
    }

    public static HttpSessionCartService getInstance() {
        return SingletonHandler.instance;
    }

    @Override
    public Cart getCartFromSource(Object src) {
        Cart cart;
        HttpSession session;
        try {
            session = (HttpSession) src;
            cart = (Cart) session.getAttribute(CART_KEY);
            if (cart == null) {
                cart = new Cart();
                session.setAttribute(CART_KEY, cart);
            }
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Source must be HttpSession");
        }
        return cart;
    }

    @Override
    public CartTransaction startTransaction(Cart cart) {
        return new CartTransaction(cart);
    }

    private static class SingletonHandler {
        static final HttpSessionCartService instance = new HttpSessionCartService();
    }
}
