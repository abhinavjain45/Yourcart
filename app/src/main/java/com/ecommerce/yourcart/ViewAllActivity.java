package com.ecommerce.yourcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {

    private RecyclerView viewAllRecyclerView;
    private GridView viewAllGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Deals of the Day");

        viewAllRecyclerView = findViewById(R.id.view_all_recycler_view);
        viewAllGridView = findViewById(R.id.view_all_grid_view);

        int LayoutCode = getIntent().getIntExtra("LayoutCode", -1);

        if (LayoutCode == 0) {
            viewAllRecyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            viewAllRecyclerView.setLayoutManager(layoutManager);

            List<WishlistModal> wishlistModalList = new ArrayList<>();
            wishlistModalList.add(new WishlistModal(R.mipmap.product1, "Product Title Here", 2, "4.8", 268, "Rs. 49,999/-", "Rs. 59,999/-", "Cash on Delivery"));
            wishlistModalList.add(new WishlistModal(R.mipmap.product2, "Product Title Here", 1, "1.8", 15, "Rs. 4,999/-", "Rs. 9,999/-", "Cash on Delivery"));
            wishlistModalList.add(new WishlistModal(R.mipmap.product3, "Product Title Here", 0, "3.7", 187, "Rs. 9,999/-", "Rs. 19,999/-", "Cash on Delivery"));
            wishlistModalList.add(new WishlistModal(R.mipmap.product1, "Product Title Here", 2, "4.8", 268, "Rs. 49,999/-", "Rs. 59,999/-", "Cash on Delivery"));
            wishlistModalList.add(new WishlistModal(R.mipmap.product2, "Product Title Here", 1, "1.8", 15, "Rs. 4,999/-", "Rs. 9,999/-", "Cash on Delivery"));
            wishlistModalList.add(new WishlistModal(R.mipmap.product3, "Product Title Here", 0, "3.7", 187, "Rs. 9,999/-", "Rs. 19,999/-", "Cash on Delivery"));
            wishlistModalList.add(new WishlistModal(R.mipmap.product1, "Product Title Here", 2, "4.8", 268, "Rs. 49,999/-", "Rs. 59,999/-", "Cash on Delivery"));
            wishlistModalList.add(new WishlistModal(R.mipmap.product2, "Product Title Here", 1, "1.8", 15, "Rs. 4,999/-", "Rs. 9,999/-", "Cash on Delivery"));
            wishlistModalList.add(new WishlistModal(R.mipmap.product3, "Product Title Here", 0, "3.7", 187, "Rs. 9,999/-", "Rs. 19,999/-", "Cash on Delivery"));
            wishlistModalList.add(new WishlistModal(R.mipmap.product1, "Product Title Here", 2, "4.8", 268, "Rs. 49,999/-", "Rs. 59,999/-", "Cash on Delivery"));
            wishlistModalList.add(new WishlistModal(R.mipmap.product2, "Product Title Here", 1, "1.8", 15, "Rs. 4,999/-", "Rs. 9,999/-", "Cash on Delivery"));
            wishlistModalList.add(new WishlistModal(R.mipmap.product3, "Product Title Here", 0, "3.7", 187, "Rs. 9,999/-", "Rs. 19,999/-", "Cash on Delivery"));
            wishlistModalList.add(new WishlistModal(R.mipmap.product1, "Product Title Here", 2, "4.8", 268, "Rs. 49,999/-", "Rs. 59,999/-", "Cash on Delivery"));
            wishlistModalList.add(new WishlistModal(R.mipmap.product2, "Product Title Here", 1, "1.8", 15, "Rs. 4,999/-", "Rs. 9,999/-", "Cash on Delivery"));
            wishlistModalList.add(new WishlistModal(R.mipmap.product3, "Product Title Here", 0, "3.7", 187, "Rs. 9,999/-", "Rs. 19,999/-", "Cash on Delivery"));
            wishlistModalList.add(new WishlistModal(R.mipmap.product1, "Product Title Here", 2, "4.8", 268, "Rs. 49,999/-", "Rs. 59,999/-", "Cash on Delivery"));
            wishlistModalList.add(new WishlistModal(R.mipmap.product2, "Product Title Here", 1, "1.8", 15, "Rs. 4,999/-", "Rs. 9,999/-", "Cash on Delivery"));
            wishlistModalList.add(new WishlistModal(R.mipmap.product3, "Product Title Here", 0, "3.7", 187, "Rs. 9,999/-", "Rs. 19,999/-", "Cash on Delivery"));

            WishlistAdapter wishlistAdapter = new WishlistAdapter(wishlistModalList, false);
            viewAllRecyclerView.setAdapter(wishlistAdapter);
            wishlistAdapter.notifyDataSetChanged();
        } else if (LayoutCode == 1) {
            viewAllGridView.setVisibility(View.VISIBLE);

            List<HorizontalProductScrollModal> horizontalProductScrollModalList = new ArrayList<>();
            horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product1,"Demo Product 1", "Product Specification", "Rs. 9543/-"));
            horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product2,"Demo Product 2", "Product Specification", "Rs. 9876/-"));
            horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product3,"Demo Product 3", "Product Specification", "Rs. 6754/-"));
            horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product1,"Demo Product 4", "Product Specification", "Rs. 1652/-"));
            horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product2,"Demo Product 5", "Product Specification", "Rs. 8765/-"));
            horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product3,"Demo Product 6", "Product Specification", "Rs. 2453/-"));
            horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product1,"Demo Product 7", "Product Specification", "Rs. 1652/-"));
            horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product2,"Demo Product 8", "Product Specification", "Rs. 1987/-"));
            horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product1,"Demo Product 1", "Product Specification", "Rs. 9543/-"));
            horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product2,"Demo Product 2", "Product Specification", "Rs. 9876/-"));
            horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product3,"Demo Product 3", "Product Specification", "Rs. 6754/-"));
            horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product1,"Demo Product 4", "Product Specification", "Rs. 1652/-"));
            horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product2,"Demo Product 5", "Product Specification", "Rs. 8765/-"));
            horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product3,"Demo Product 6", "Product Specification", "Rs. 2453/-"));
            horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product1,"Demo Product 7", "Product Specification", "Rs. 1652/-"));
            horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product2,"Demo Product 8", "Product Specification", "Rs. 1987/-"));

            GridProductLayoutAdapter gridProductLayoutAdapter = new GridProductLayoutAdapter(horizontalProductScrollModalList);
            viewAllGridView.setAdapter(gridProductLayoutAdapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}