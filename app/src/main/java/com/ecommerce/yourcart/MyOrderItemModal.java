package com.ecommerce.yourcart;

public class MyOrderItemModal {
    private int ordersProductImage;
    private int ratings;
    private String ordersProductTitle;
    private String orderStatus;

    public MyOrderItemModal(int ordersProductImage, int ratings, String ordersProductTitle, String orderStatus) {
        this.ordersProductImage = ordersProductImage;
        this.ratings = ratings;
        this.ordersProductTitle = ordersProductTitle;
        this.orderStatus = orderStatus;
    }

    public int getOrdersProductImage() {
        return ordersProductImage;
    }

    public void setOrdersProductImage(int ordersProductImage) {
        this.ordersProductImage = ordersProductImage;
    }

    public int getRatings() {
        return ratings;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }

    public String getOrdersProductTitle() {
        return ordersProductTitle;
    }

    public void setOrdersProductTitle(String ordersProductTitle) {
        this.ordersProductTitle = ordersProductTitle;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
