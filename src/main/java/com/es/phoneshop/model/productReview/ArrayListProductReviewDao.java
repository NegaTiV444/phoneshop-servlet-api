package com.es.phoneshop.model.productReview;

import com.es.phoneshop.model.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayListProductReviewDao implements ProductReviewDao{

    private ArrayListProductReviewDao() {
    }

    private static class SingletonHolder {
        private static final ArrayListProductReviewDao INSTANCE = new ArrayListProductReviewDao();
    }

    public static ArrayListProductReviewDao getInstance() {
        return SingletonHolder.INSTANCE;

    }

    private List<ProductReview> reviews = new ArrayList<>();

    @Override
    public void addReview(ProductReview review) {
        reviews.add(review);
    }

    @Override
    public List<ProductReview> findReviews(Product product) {
        return reviews.stream()
                .filter(review -> review.getProduct().equals(product))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductReview> findReviews() {
        return reviews;
    }

    @Override
    public ProductReview getReview(String uid) {
        return reviews.stream()
                .filter(review -> review.getUid().equals(uid))
                .findFirst().orElseThrow(IllegalAccessError::new);
    }

}
