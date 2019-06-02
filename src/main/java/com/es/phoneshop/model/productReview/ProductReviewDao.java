package com.es.phoneshop.model.productReview;

import com.es.phoneshop.model.product.Product;

import java.util.List;

public interface ProductReviewDao {

    void addReview(ProductReview review);
    List<ProductReview> findReviews(Product product);
    List<ProductReview> findReviews();
    ProductReview getReview (String uid);
}
