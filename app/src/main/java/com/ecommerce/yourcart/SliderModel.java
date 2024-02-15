package com.ecommerce.yourcart;

public class SliderModel {
    private int banner;

    public int getBanner() {
        return banner;
    }

    public void setBanner(int banner) {
        this.banner = banner;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public SliderModel(int banner, String backgroundColor) {
        this.banner = banner;
        this.backgroundColor = backgroundColor;
    }

    private String backgroundColor;
}