package com.ecommerce.yourcart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class DeliveryActivity extends AppCompatActivity {

    private RecyclerView deliveryRecyclerView;
    private Button changeOrAddNewAddressButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Delivery");

        deliveryRecyclerView = findViewById(R.id.delivery_recycler_view);
        changeOrAddNewAddressButton = findViewById(R.id.change_or_add_address_button);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        deliveryRecyclerView.setLayoutManager(layoutManager);
        List<CartItemModal> cartItemModalList = new ArrayList<>();
        cartItemModalList.add(new CartItemModal(0, R.mipmap.product1, "Product Title Here", 2, "Rs. 49,999/-", "Rs. 59,999/-", 1, 0, 0));
        cartItemModalList.add(new CartItemModal(0, R.mipmap.product2, "Product Title Here", 0, "Rs. 49,999/-", "Rs. 59,999/-", 1, 1, 0));
        cartItemModalList.add(new CartItemModal(0, R.mipmap.product1, "Product Title Here", 2, "Rs. 49,999/-", "Rs. 59,999/-", 1, 0, 0));
        cartItemModalList.add(new CartItemModal(1, "3", "Rs. 1,49,999/-", "Free", "Rs. 1,49,999/-", "29,999"));

        CartAdapter cartAdapter = new CartAdapter(cartItemModalList);
        deliveryRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        changeOrAddNewAddressButton.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}