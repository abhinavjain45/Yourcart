package com.ecommerce.yourcart;

public class HorizontalProductScrollModal {
    private int productImage;
    private String productTitle;
    private String productSpecification;
    private String productPrice;

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductSpecification() {
        return productSpecification;
    }

    public void setProductSpecification(String productSpecification) {
        this.productSpecification = productSpecification;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public HorizontalProductScrollModal(int productImage, String productTitle, String productSpecification, String productPrice) {
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.productSpecification = productSpecification;
        this.productPrice = productPrice;
    }
}
