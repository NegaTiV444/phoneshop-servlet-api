package com.es.phoneshop.model.productReview;

import com.es.phoneshop.model.product.Product;

import java.util.UUID;

public class ProductReview {

    private String name;
    private String comment;
    private String uid;
    private boolean isApproved;

    public String getUid() {
        return uid;
    }

    private int rating;
    private Product product;

    public ProductReview() {
        uid = UUID.randomUUID().toString();
        isApproved = false; //TODO make false and add approving
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean allowed) {
        isApproved = allowed;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
