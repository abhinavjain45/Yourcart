package com.ecommerce.yourcart;

import static com.ecommerce.yourcart.DataBaseQueries.listHomePageModalList;
import static com.ecommerce.yourcart.DataBaseQueries.loadFragmentData;
import static com.ecommerce.yourcart.DataBaseQueries.loadedCategoriesNames;

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
    private List<HomePageModal> homePageModalPlaceholderList = new ArrayList<>();
    private HomePageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<SliderModel> sliderModelPlaceholderList = new ArrayList<>();
        sliderModelPlaceholderList.add(new SliderModel("null", "#FFFFFF"));
        sliderModelPlaceholderList.add(new SliderModel("null", "#FFFFFF"));
        sliderModelPlaceholderList.add(new SliderModel("null", "#FFFFFF"));
        sliderModelPlaceholderList.add(new SliderModel("null", "#FFFFFF"));

        List<HorizontalProductScrollModal> horizontalProductScrollModalPlaceholderList = new ArrayList<>();
        horizontalProductScrollModalPlaceholderList.add(new HorizontalProductScrollModal("", "", "", "", ""));
        horizontalProductScrollModalPlaceholderList.add(new HorizontalProductScrollModal("", "", "", "", ""));
        horizontalProductScrollModalPlaceholderList.add(new HorizontalProductScrollModal("", "", "", "", ""));
        horizontalProductScrollModalPlaceholderList.add(new HorizontalProductScrollModal("", "", "", "", ""));
        horizontalProductScrollModalPlaceholderList.add(new HorizontalProductScrollModal("", "", "", "", ""));
        horizontalProductScrollModalPlaceholderList.add(new HorizontalProductScrollModal("", "", "", "", ""));
        horizontalProductScrollModalPlaceholderList.add(new HorizontalProductScrollModal("", "", "", "", ""));

        homePageModalPlaceholderList.add(new HomePageModal(0, sliderModelPlaceholderList));
        homePageModalPlaceholderList.add(new HomePageModal(1, "", "#FFFFFF"));
        homePageModalPlaceholderList.add(new HomePageModal(2, "", horizontalProductScrollModalPlaceholderList, new ArrayList<WishlistModal>()));
        homePageModalPlaceholderList.add(new HomePageModal(3, "", horizontalProductScrollModalPlaceholderList));

        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categoryRecyclerView = findViewById(R.id.category_recycler_view);

        /////////////////////////////////////////
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLayoutManager);

        adapter = new HomePageAdapter(homePageModalPlaceholderList);

        int listPosition = 0;
        for (int x = 0; x < loadedCategoriesNames.size(); x++) {
            if (loadedCategoriesNames.get(x).equals(title.toUpperCase())) {
                listPosition = x;
            }
        }

        if (listPosition == 0) {
            loadedCategoriesNames.add(title.toUpperCase());
            listHomePageModalList.add(new ArrayList<HomePageModal>());
            loadFragmentData(categoryRecyclerView, this, loadedCategoriesNames.size() - 1, title);
        } else {
            adapter = new HomePageAdapter(listHomePageModalList.get(listPosition));
        }

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