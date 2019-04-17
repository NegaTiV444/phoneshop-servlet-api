package com.es.phoneshop.model.productReview;

import com.es.phoneshop.model.product.Product;

public class ProductReviewServiceImpl implements ProductReviewService {

    private final ProductReviewDao productReviewDao = ArrayListProductReviewDao.getInstance();

    private ProductReviewServiceImpl() {
    }

    @Override
    public void approveReview(ProductReview review) {
        review.setApproved(true);
    }

    @Override
    public void addReview(String name, int rating, String comment, Product product) {
        ProductReview productReview = new ProductReview();
        productReview.setName(name);
        productReview.setRating(rating);
        productReview.setComment(comment);
        productReview.setProduct(product);
        productReviewDao.addReview(productReview);
    }

    private static class SingletonHolder {
        private static final ProductReviewServiceImpl INSTANCE = new ProductReviewServiceImpl();
    }

    public static ProductReviewServiceImpl getInstance() {
        return SingletonHolder.INSTANCE;

    }


}
