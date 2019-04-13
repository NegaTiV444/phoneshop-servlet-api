package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.enums.DeliveryMethod;
import com.es.phoneshop.model.enums.PaymentMethod;

import java.math.BigDecimal;

public class Order extends Cart {

    private Cart cart;

    private String UID;
    private String address;
    private String firstName;
    private String lastName;
    private String phone;

    @Override
    public BigDecimal getTotalPrice() {
        BigDecimal deliveryPrice;
        switch (deliveryMethod){
            case COURIER:
                deliveryPrice = BigDecimal.valueOf(5);
                break;
            default:
                deliveryPrice = BigDecimal.ZERO;
        }
        return super.getTotalPrice().add(deliveryPrice);
    }

    public Cart getCart() {
        return cart;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    private DeliveryMethod deliveryMethod;
    private PaymentMethod paymentMethod;

    public void setCart(Cart cart) {
        this.cart = cart;
        getItems().clear();
        cart.getItems().forEach(cartItem -> this.getItems().add(new CartItem(cartItem)));
        this.setTotalPrice(cart.getTotalPrice());
        this.setTotalProducts(cart.getTotalProducts());
    }

    public Order() {
    }
}
