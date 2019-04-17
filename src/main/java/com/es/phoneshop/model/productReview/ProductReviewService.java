package com.es.phoneshop.model.productReview;

import com.es.phoneshop.model.product.Product;

public interface ProductReviewService {

    void approveReview(ProductReview review);
    void addReview(String name, int rating, String comment, Product product);
}
