package com.ecommerce.yourcart;

import static com.ecommerce.yourcart.DeliveryActivity.SELECT_ADDRESS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MyAddressesActivity extends AppCompatActivity {

    private RecyclerView allAddressesRecyclerView;
    private Button deliverHereButton;
    private static AddressesAdapter addressesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_addresses);
        androidx.appcompat.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My Addresses");

        allAddressesRecyclerView = findViewById(R.id.addresses_recycler_view);
        deliverHereButton = findViewById(R.id.deliver_here_button);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        allAddressesRecyclerView.setLayoutManager(layoutManager);

        List<AddressesModal> addressesModalList = new ArrayList<>();
        addressesModalList.add(new AddressesModal("Abhinav Jain", "Prem Villa, Vivekanand Colony, Deoli, Tonk, Rajasthan", "304804", true));
        addressesModalList.add(new AddressesModal("Arpan Jain", "123, Green Park Avenue, Kolkata, West Bengal", "700064", false));
        addressesModalList.add(new AddressesModal("Arpit Agarwal", "45/2, Mahatma Gandhi Road, Chennai, Tamil Nadu", "600020", false));
        addressesModalList.add(new AddressesModal("Sudeep Jacob", "Flat No. 201, Lotus Apartments, Sector 15, Noida, Uttar Pradesh", "289371", false));
        addressesModalList.add(new AddressesModal("Priya Patel", "House No. 789, Patel Nagar, New Delhi, Delhi", "110008", false));
        addressesModalList.add(new AddressesModal("Rajesh Sharma", "67, Hill View Colony, Jaipur, Rajasthan", "302004", false));
        addressesModalList.add(new AddressesModal("Nisha Sharma", "B-12, Krishna Vihar, Vijayawada, Andhra Pradesh", "520010", false));

        int mode = getIntent().getIntExtra("MODE", -1);

        if (mode == SELECT_ADDRESS) {
            deliverHereButton.setVisibility(View.VISIBLE);
        } else {
            deliverHereButton.setVisibility(View.GONE);
        }

        addressesAdapter = new AddressesAdapter(addressesModalList, mode);
        allAddressesRecyclerView.setAdapter(addressesAdapter);
        ((SimpleItemAnimator)allAddressesRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        addressesAdapter.notifyDataSetChanged();
    }

    public static void refreshItem(int deselectPosition, int selectPosition) {
        addressesAdapter.notifyItemChanged(deselectPosition);
        addressesAdapter.notifyItemChanged(selectPosition);
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