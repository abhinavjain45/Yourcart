package com.ecommerce.yourcart;

public class CategoryModal {

    private String categoryIconLink;
    private String categoryName;

    public CategoryModal(String categoryIconLink, String categoryName) {
        this.categoryIconLink = categoryIconLink;
        this.categoryName = categoryName;
    }

    public String getCategoryIconLink() {
        return categoryIconLink;
    }

    public void setCategoryIconLink(String categoryIconLink) {
        this.categoryIconLink = categoryIconLink;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
