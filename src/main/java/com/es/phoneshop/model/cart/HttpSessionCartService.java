package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;

public class HttpSessionCartService implements CartService{

    private HttpSessionCartService(){}

    private static class SingletonHandler{
        static final HttpSessionCartService instance = new HttpSessionCartService();
    }

    public static HttpSessionCartService newInstance(){
        return SingletonHandler.instance;
    }

    @Override
    public void addToCart(Cart cart, Product product, int quantity) throws OutOfStockException {
        CartItem cartItem = cart.getItems().stream()
                .filter(cartItem1 -> cartItem1.getProduct().equals(product)
                ).findAny().orElse(null);
        if (product.getStock() < quantity){
            throw new OutOfStockException();
        }
        if (cartItem != null){
            int newQuantity = cartItem.getQuantity() + quantity;
            if (cartItem.getProduct().getStock() < newQuantity){
                throw new OutOfStockException();
            }
            else{
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
            }
        } else {
            cart.getItems().add(new CartItem(product, quantity));
        }
    }
}
