package com.ecommerce.yourcart;

public class WishlistModal {

    private String wishlistProductID;
    private String wishlistProductImage;
    private String wishlistProductTitle;
    private long wishlistFreeCouponNumber;
    private String productAverageRating;
    private long productTotalRatings;
    private String wishlistProductPrice;
    private String wishlistCuttedPrice;
    private Boolean availablePaymentMethod;

    public WishlistModal(String wishlistProductID, String wishlistProductImage, String wishlistProductTitle, long wishlistFreeCouponNumber, String productAverageRating, long productTotalRatings, String wishlistProductPrice, String wishlistCuttedPrice, Boolean availablePaymentMethod) {
        this.wishlistProductID = wishlistProductID;
        this.wishlistProductImage = wishlistProductImage;
        this.wishlistProductTitle = wishlistProductTitle;
        this.wishlistFreeCouponNumber = wishlistFreeCouponNumber;
        this.productAverageRating = productAverageRating;
        this.productTotalRatings = productTotalRatings;
        this.wishlistProductPrice = wishlistProductPrice;
        this.wishlistCuttedPrice = wishlistCuttedPrice;
        this.availablePaymentMethod = availablePaymentMethod;
    }

    public String getWishlistProductID() {
        return wishlistProductID;
    }

    public void setWishlistProductID(String wishlistProductID) {
        this.wishlistProductID = wishlistProductID;
    }

    public String getWishlistProductImage() {
        return wishlistProductImage;
    }

    public void setWishlistProductImage(String wishlistProductImage) {
        this.wishlistProductImage = wishlistProductImage;
    }

    public String getWishlistProductTitle() {
        return wishlistProductTitle;
    }

    public void setWishlistProductTitle(String wishlistProductTitle) {
        this.wishlistProductTitle = wishlistProductTitle;
    }

    public long getWishlistFreeCouponNumber() {
        return wishlistFreeCouponNumber;
    }

    public void setWishlistFreeCouponNumber(long wishlistFreeCouponNumber) {
        this.wishlistFreeCouponNumber = wishlistFreeCouponNumber;
    }

    public String getProductAverageRating() {
        return productAverageRating;
    }

    public void setProductAverageRating(String productAverageRating) {
        this.productAverageRating = productAverageRating;
    }

    public long getProductTotalRatings() {
        return productTotalRatings;
    }

    public void setProductTotalRatings(long productTotalRatings) {
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

    public Boolean getAvailablePaymentMethod() {
        return availablePaymentMethod;
    }

    public void setAvailablePaymentMethod(Boolean availablePaymentMethod) {
        this.availablePaymentMethod = availablePaymentMethod;
    }
}
