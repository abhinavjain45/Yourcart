package com.ecommerce.yourcart;

public class WishlistModal {
    private int wishlistProductImage;
    private String wishlistProductTitle;
    private int wishlistFreeCouponNumber;
    private String productAverageRating;
    private int productTotalRatings;
    private String wishlistProductPrice;
    private String wishlistCuttedPrice;
    private String availablePaymentMethod;

    public WishlistModal(int wishlistProductImage, String wishlistProductTitle, int wishlistFreeCouponNumber, String productAverageRating, int productTotalRatings, String wishlistProductPrice, String wishlistCuttedPrice, String availablePaymentMethod) {
        this.wishlistProductImage = wishlistProductImage;
        this.wishlistProductTitle = wishlistProductTitle;
        this.wishlistFreeCouponNumber = wishlistFreeCouponNumber;
        this.productAverageRating = productAverageRating;
        this.productTotalRatings = productTotalRatings;
        this.wishlistProductPrice = wishlistProductPrice;
        this.wishlistCuttedPrice = wishlistCuttedPrice;
        this.availablePaymentMethod = availablePaymentMethod;
    }

    public int getWishlistProductImage() {
        return wishlistProductImage;
    }

    public void setWishlistProductImage(int wishlistProductImage) {
        this.wishlistProductImage = wishlistProductImage;
    }

    public String getWishlistProductTitle() {
        return wishlistProductTitle;
    }

    public void setWishlistProductTitle(String wishlistProductTitle) {
        this.wishlistProductTitle = wishlistProductTitle;
    }

    public int getWishlistFreeCouponNumber() {
        return wishlistFreeCouponNumber;
    }

    public void setWishlistFreeCouponNumber(int wishlistFreeCouponNumber) {
        this.wishlistFreeCouponNumber = wishlistFreeCouponNumber;
    }

    public String getProductAverageRating() {
        return productAverageRating;
    }

    public void setProductAverageRating(String productAverageRating) {
        this.productAverageRating = productAverageRating;
    }

    public int getProductTotalRatings() {
        return productTotalRatings;
    }

    public void setProductTotalRatings(int productTotalRatings) {
        this.productTotalRatings = productTotalRatings;
    }

    public String getWishlistProductPrice() {
        return wishlistProductPrice;
    }

    public void setWishlistProductPrice(String wishlistProductPrice) {
        this.wishlistProductPrice = wishlistProductPrice;
    }

    public String getWishlistCuttedPrice() {
        return wishlistCuttedPrice;
    }

    public void setWishlistCuttedPrice(String wishlistCuttedPrice) {
        this.wishlistCuttedPrice = wishlistCuttedPrice;
    }

    public String getAvailablePaymentMethod() {
        return availablePaymentMethod;
    }

    public void setAvailablePaymentMethod(String availablePaymentMethod) {
        this.availablePaymentMethod = availablePaymentMethod;
    }
}
