package com.ecommerce.yourcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {

    private Dialog loadingDialog;

    private EditText city;
    private EditText locality;
    private EditText flatNumber;
    private EditText pincode;
    private Spinner state;
    private EditText landmark;
    private EditText customerName;
    private EditText mobileNumber;
    private EditText alternateMobileNumber;
    private Button saveAddressButton;

    private String [] stateList;
    private String selectedState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Add new Address");

        ////Loading Dialog
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(this.getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ////Loading Dialog

        city = findViewById(R.id.add_address_city);
        locality = findViewById(R.id.add_address_locality);
        flatNumber = findViewById(R.id.add_address_flat_number);
        pincode = findViewById(R.id.add_address_pincode);
        state = findViewById(R.id.add_address_spinner);
        landmark = findViewById(R.id.add_address_landmark);
        customerName = findViewById(R.id.add_address_name);
        mobileNumber = findViewById(R.id.add_address_mobile_number);
        alternateMobileNumber = findViewById(R.id.add_address_alternate_mobile_number);
        saveAddressButton = findViewById(R.id.add_address_save_button);

        stateList = getResources().getStringArray(R.array.india_states);

        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, stateList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(spinnerAdapter);

        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedState = stateList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        saveAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(city.getText())) {
                    if (!TextUtils.isEmpty(locality.getText())) {
                        if (!TextUtils.isEmpty(flatNumber.getText())) {
                            if (!TextUtils.isEmpty(pincode.getText()) && pincode.getText().length() == 6) {
                                if (!TextUtils.isEmpty(customerName.getText())) {
                                    if (!TextUtils.isEmpty(mobileNumber.getText()) && mobileNumber.getText().length() == 10) {
                                        loadingDialog.show();
                                        final String fullAddress;
                                        final String fullName;

                                        fullName = customerName.getText().toString();

                                        Map<String, Object> addAddress = new HashMap<>();
                                        addAddress.put("listSize", (long) DataBaseQueries.addressesModalList.size() + 1);
                                        addAddress.put("fullName" + String.valueOf((long) DataBaseQueries.addressesModalList.size() + 1), fullName);
                                        addAddress.put("mobileNumber" + String.valueOf((long) DataBaseQueries.addressesModalList.size() + 1), mobileNumber.getText().toString());
                                        if (!TextUtils.isEmpty(alternateMobileNumber.getText())) {
                                            addAddress.put("alternateMobileNumber" + String.valueOf((long) DataBaseQueries.addressesModalList.size() + 1), alternateMobileNumber.getText().toString());
                                        } else {
                                            addAddress.put("alternateMobileNumber" + String.valueOf((long) DataBaseQueries.addressesModalList.size() + 1), "");
                                        }

                                        if (!TextUtils.isEmpty(landmark.getText())) {
                                            fullAddress = flatNumber.getText().toString() + ", " + locality.getText().toString() + ", " + landmark.getText().toString() + ", " + city.getText().toString() + ", " + selectedState;
                                        } else {
                                            fullAddress = flatNumber.getText().toString() + ", " + locality.getText().toString() + ", " + city.getText().toString() + ", " + selectedState;
                                        }

                                        addAddress.put("address" + String.valueOf((long) DataBaseQueries.addressesModalList.size() + 1), fullAddress);
                                        addAddress.put("pincode" + String.valueOf((long) DataBaseQueries.addressesModalList.size() + 1), pincode.getText().toString());
                                        addAddress.put("selected" + String.valueOf((long) DataBaseQueries.addressesModalList.size() + 1), true);
                                        if (DataBaseQueries.addressesModalList.size() > 0) {
                                            addAddress.put("selected" + (DataBaseQueries.selectedAddress + 1), false);
                                        }

                                        FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("ADDRESSES_DATA")
                                                .update(addAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            if (DataBaseQueries.addressesModalList.size() > 0) {
                                                                DataBaseQueries.addressesModalList.get(DataBaseQueries.selectedAddress).setSelectedAddress(false);
                                                            }
                                                            DataBaseQueries.addressesModalList.add(new AddressesModal(fullName, mobileNumber.getText().toString(), alternateMobileNumber.getText().toString(), fullAddress, pincode.getText().toString(), true));

                                                            if (getIntent().getStringExtra("INTENT").equals("deliveryIntent")) {
                                                                Intent deliveryIntent = new Intent(AddAddressActivity.this, DeliveryActivity.class);
                                                                startActivity(deliveryIntent);
                                                            } else {
                                                                MyAddressesActivity.refreshItem(DataBaseQueries.selectedAddress, DataBaseQueries.addressesModalList.size() - 1);
                                                            }
                                                            DataBaseQueries.selectedAddress = DataBaseQueries.addressesModalList.size() - 1;
                                                            finish();
                                                        } else {
                                                            String error = task.getException().getMessage();
                                                            Toast.makeText(AddAddressActivity.this, error, Toast.LENGTH_SHORT).show();
                                                        }
                                                        loadingDialog.dismiss();
                                                    }
                                                });
                                    } else {
                                        mobileNumber.requestFocus();
                                        Toast.makeText(AddAddressActivity.this, "Please provide valid Mobile Number!", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    customerName.requestFocus();
                                }
                            } else {
                                pincode.requestFocus();
                                Toast.makeText(AddAddressActivity.this, "Please provide valid Pincode!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            flatNumber.requestFocus();
                        }
                    } else {
                        locality.requestFocus();
                    }
                } else {
                    city.requestFocus();
                }
            }
        });
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