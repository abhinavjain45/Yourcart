package com.ecommerce.yourcart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categoryRecyclerView = findViewById(R.id.category_recycler_view);

        /////// Banner Slider
        List<SliderModel> sliderModelList = new ArrayList<SliderModel>();

        sliderModelList.add(new SliderModel(R.mipmap.slider2, "#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.slider3, "#077AE4"));

        sliderModelList.add(new SliderModel(R.mipmap.slider1, "#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.slider2, "#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.slider3, "#077AE4"));

        sliderModelList.add(new SliderModel(R.mipmap.slider1, "#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.slider2, "#077AE4"));
        /////// Banner Slider

        /////// Horizontal Product Layout
        List<HorizontalProductScrollModal> horizontalProductScrollModalList = new ArrayList<>();
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product1,"Demo Product 1", "Product Specification", "Rs. 9543/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product2,"Demo Product 2", "Product Specification", "Rs. 9876/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product3,"Demo Product 3", "Product Specification", "Rs. 6754/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product1,"Demo Product 4", "Product Specification", "Rs. 1652/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product2,"Demo Product 5", "Product Specification", "Rs. 8765/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product3,"Demo Product 6", "Product Specification", "Rs. 2453/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product1,"Demo Product 7", "Product Specification", "Rs. 1652/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product2,"Demo Product 8", "Product Specification", "Rs. 1987/-"));
        /////// Horizontal Product Layout

        /////////////////////////////////////////
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLayoutManager);

        List<HomePageModal> homePageModalList = new ArrayList<>();
        homePageModalList.add(new HomePageModal(0, sliderModelList));
        homePageModalList.add(new HomePageModal(1, R.mipmap.slider3, "#000000"));
        homePageModalList.add(new HomePageModal(2, "Deals of the Day", horizontalProductScrollModalList));
        homePageModalList.add(new HomePageModal(3, "#trending", horizontalProductScrollModalList));

        HomePageAdapter adapter = new HomePageAdapter(homePageModalList);
        categoryRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.main_search_icon) {
            return true;
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}