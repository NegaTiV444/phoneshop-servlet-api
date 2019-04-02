package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class HttpSessionCartService implements CartService {

    private static final String CART_KEY = "cart";

    private HttpSessionCartService() {
    }

    public static HttpSessionCartService newInstance() {
        return SingletonHandler.instance;
    }

    @Override
    public void addToCart(Cart cart, Product product, int quantity) throws OutOfStockException {
        Optional<CartItem> cartItemOptional = cart.getItems().stream()
                .filter(cartItem1 -> cartItem1.getProduct().equals(product))
                .findAny();
        int newQuantity;
        newQuantity = cartItemOptional.map(CartItem::getQuantity).orElse(0);
        if (product.getStock() < quantity + newQuantity) {
            throw new OutOfStockException();
        } else if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            cart.getItems().add(new CartItem(product, quantity));
        }
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

    private static class SingletonHandler {
        static final HttpSessionCartService instance = new HttpSessionCartService();
    }
}
