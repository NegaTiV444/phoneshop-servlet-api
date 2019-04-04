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

//    @Override
//    public void addOrUpdate(Cart cart, Product product, int quantity, boolean isUpdate) throws OutOfStockException {
//        Optional<CartItem> cartItemOptional = cart.getItems().stream()
//                .filter(cartItem1 -> cartItem1.getProduct().equals(product))
//                .findAny();
//        int newQuantity;
//        newQuantity = isUpdate ? quantity : cartItemOptional.map(CartItem::getQuantity).orElse(0) + quantity;
//        if (product.getStock() < newQuantity) {
//            throw new OutOfStockException();
//        } else if (cartItemOptional.isPresent()) {
//            CartItem cartItem = cartItemOptional.get();
//            if (newQuantity == 0) {
//                cart.getItems().remove(cartItem);
//            } else {
//                cartItem.setQuantity(newQuantity);
//            }
//        } else {
//            cart.getItems().add(new CartItem(product, quantity));
//        }
//        cart.recalculateTotalPrice();
//    }

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

//    public void updateCart(HttpServletRequest request){
//        Cart cart = getCartFromSource(request.getSession());
//        String[] quantityValues = request.getParameterValues("quantity");
//        int i = 0;
//        for (String quantityValue : quantityValues){
//            try {
//                Integer.parseInt(quantityValue);
//
//            } catch (NumberFormatException e){
//                request.setAttribute();
//            }
//        }
//    }

    private static class SingletonHandler {
        static final HttpSessionCartService instance = new HttpSessionCartService();
    }
}
