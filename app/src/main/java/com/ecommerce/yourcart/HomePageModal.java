package com.ecommerce.yourcart;

import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomePageModal {
    public static final int BANNER_SLIDER = 0;
    public static final int STRIP_AD_BANNER = 1;
    public static final int HORIZONTAL_PRODUCT_VIEW = 2;
    public static final int GRID_PRODUCT_VIEW = 3;
    private int type;

    ////// Banner Slider
    private List<SliderModel> sliderModelList;

    public HomePageModal(int type, List<SliderModel> sliderModelList) {
        this.type = type;
        this.sliderModelList = sliderModelList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<SliderModel> getSliderModelList() {
        return sliderModelList;
    }
    public void setSliderModelList(List<SliderModel> sliderModelList) {
        this.sliderModelList = sliderModelList;
    }
    //// Banner Slider

    ///// Strip Ad Banner
    private String resource;
    private String backgroundColor;

    public HomePageModal(int type, String resource, String backgroundColor) {
        this.type = type;
        this.resource = resource;
        this.backgroundColor = backgroundColor;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    ///// Strip Ad Banner

    ///// Horizontal Product & Grid Product
    private String title;
    private List<HorizontalProductScrollModal> horizontalProductScrollModalList;

    public HomePageModal(int type, String title, List<HorizontalProductScrollModal> horizontalProductScrollModalList) {
        this.type = type;
        this.title = title;
        this.horizontalProductScrollModalList = horizontalProductScrollModalList;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public List<HorizontalProductScrollModal> getHorizontalProductScrollModalList() {
        return horizontalProductScrollModalList;
    }
    public void setHorizontalProductScrollModalList(List<HorizontalProductScrollModal> horizontalProductScrollModalList) {
        this.horizontalProductScrollModalList = horizontalProductScrollModalList;
    }
    ///// Horizontal Product & Grid Product
}
