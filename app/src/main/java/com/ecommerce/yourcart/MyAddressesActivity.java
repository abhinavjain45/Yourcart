package com.ecommerce.yourcart;

import static com.ecommerce.yourcart.DeliveryActivity.SELECT_ADDRESS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAddressesActivity extends AppCompatActivity {

    private RecyclerView allAddressesRecyclerView;
    private static AddressesAdapter addressesAdapter;
    private Dialog loadingDialog;
    private int previousSelectedAddress;

    private LinearLayout addNewAddressButton;
    private TextView addressesCount;
    private Button deliverHereButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_addresses);
        androidx.appcompat.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My Addresses");

        ////Loading Dialog
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(this.getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ////Loading Dialog

        previousSelectedAddress = DataBaseQueries.selectedAddress;

        allAddressesRecyclerView = findViewById(R.id.addresses_recycler_view);
        addressesCount = findViewById(R.id.saved_address_count);
        deliverHereButton = findViewById(R.id.deliver_here_button);
        addNewAddressButton = findViewById(R.id.add_new_address_button);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        allAddressesRecyclerView.setLayoutManager(layoutManager);

        int mode = getIntent().getIntExtra("MODE", -1);

        if (mode == SELECT_ADDRESS) {
            deliverHereButton.setVisibility(View.VISIBLE);
        } else {
            deliverHereButton.setVisibility(View.GONE);
        }

        addressesAdapter = new AddressesAdapter(DataBaseQueries.addressesModalList, mode);
        allAddressesRecyclerView.setAdapter(addressesAdapter);
        ((SimpleItemAnimator)allAddressesRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        addressesAdapter.notifyDataSetChanged();

        addNewAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addAddressIntent = new Intent(MyAddressesActivity.this, AddAddressActivity.class);
                addAddressIntent.putExtra("INTENT", "addAddressIntent");
                startActivity(addAddressIntent);
            }
        });

        deliverHereButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DataBaseQueries.selectedAddress != previousSelectedAddress) {
                    final int previousSelectedIndex = previousSelectedAddress;
                    loadingDialog.show();

                    Map<String, Object> updateSelectedAddress = new HashMap<>();
                    updateSelectedAddress.put("selected" + String.valueOf(previousSelectedAddress + 1), false);
                    updateSelectedAddress.put("selected" + String.valueOf(DataBaseQueries.selectedAddress + 1), true);

                    previousSelectedAddress = DataBaseQueries.selectedAddress;
                    FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("ADDRESSES_DATA")
                            .update(updateSelectedAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        finish();
                                    } else {
                                        previousSelectedAddress = previousSelectedIndex;
                                        String error = task.getException().getMessage();
                                        Toast.makeText(MyAddressesActivity.this, error, Toast.LENGTH_SHORT).show();
                                    }
                                    loadingDialog.dismiss();
                                }
                            });
                } else {
                    finish();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (DataBaseQueries.addressesModalList.size() == 0) {
            addressesCount.setText("No Addresses Available");
        } else if (DataBaseQueries.addressesModalList.size() == 1) {
            addressesCount.setText(String.valueOf(DataBaseQueries.addressesModalList.size()) + " Saved Address");
        } else {
            addressesCount.setText(String.valueOf(DataBaseQueries.addressesModalList.size()) + " Saved Addresses");
        }
    }

    public static void refreshItem(int deselectPosition, int selectPosition) {
        addressesAdapter.notifyItemChanged(deselectPosition);
        addressesAdapter.notifyItemChanged(selectPosition);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (DataBaseQueries.selectedAddress != previousSelectedAddress) {
                DataBaseQueries.addressesModalList.get(DataBaseQueries.selectedAddress).setSelectedAddress(false);
                DataBaseQueries.addressesModalList.get(previousSelectedAddress).setSelectedAddress(true);
                DataBaseQueries.selectedAddress = previousSelectedAddress;
            }
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (DataBaseQueries.selectedAddress != previousSelectedAddress) {
            DataBaseQueries.addressesModalList.get(DataBaseQueries.selectedAddress).setSelectedAddress(false);
            DataBaseQueries.addressesModalList.get(previousSelectedAddress).setSelectedAddress(true);
            DataBaseQueries.selectedAddress = previousSelectedAddress;
        }
        super.onBackPressed();
    }
}