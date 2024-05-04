package com.ecommerce.yourcart;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DeliveryActivity extends AppCompatActivity implements PaymentResultListener {

    private RecyclerView deliveryRecyclerView;
    private Boolean successResponse = false;
    public static boolean fromCart;
    public static boolean codOrderConfirmed = false;
    public static final int SELECT_ADDRESS = 0;

    ///// Order Confirmation
    private ConstraintLayout orderConfirmationLayout;
    private TextView orderIDTextView;
    private String orderID;
    private ImageButton continueShoppingButton;
    ///// Order Confirmation

    private TextView fullName;
    private TextView fullAddress;
    private TextView pincode;
    private Button changeOrAddNewAddressButton;
    private TextView cartTotalAmount;
    private Button deliveryContinueButton;

    public static List<CartItemModal> cartItemModalList;

    private Dialog loadingDialog;
    private Dialog paymentMethodDialog;
    private ImageButton razorpayButton;
    private ImageButton codButton;

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
        fullName = findViewById(R.id.shipping_details_full_name);
        fullAddress = findViewById(R.id.shipping_details_full_address);
        pincode = findViewById(R.id.shipping_details_pincode);
        changeOrAddNewAddressButton = findViewById(R.id.change_or_add_address_button);
        cartTotalAmount = findViewById(R.id.total_cart_amount);
        deliveryContinueButton = findViewById(R.id.cart_continue_button);

        orderID = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 12);

        ////Loading Dialog
        loadingDialog = new Dialog(DeliveryActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ////Loading Dialog

        ////Payment Method Dialog
        paymentMethodDialog = new Dialog(DeliveryActivity.this);
        paymentMethodDialog.setContentView(R.layout.payment_dialog);
        paymentMethodDialog.setCancelable(true);
        paymentMethodDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        paymentMethodDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ////Payment Method Dialog

        //// Order Confirmation
        orderConfirmationLayout = findViewById(R.id.order_confirmation_layout);
        orderIDTextView = findViewById(R.id.order_confirmation_order_id);
        continueShoppingButton = findViewById(R.id.continue_shopping_button);
        //// Order Confirmation

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        deliveryRecyclerView.setLayoutManager(layoutManager);

        CartAdapter cartAdapter = new CartAdapter(cartItemModalList, cartTotalAmount, false);
        deliveryRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        changeOrAddNewAddressButton.setVisibility(View.VISIBLE);

        changeOrAddNewAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myAddressesIntent = new Intent(DeliveryActivity.this, MyAddressesActivity.class);
                myAddressesIntent.putExtra("MODE", SELECT_ADDRESS);
                startActivity(myAddressesIntent);
            }
        });

        deliveryContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentMethodDialog.show();
            }
        });

        razorpayButton = paymentMethodDialog.findViewById(R.id.razorpay_button);
        razorpayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentMethodDialog.dismiss();

                startPayment();
            }
        });

        codButton = paymentMethodDialog.findViewById(R.id.cod_button);
        codButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentMethodDialog.dismiss();
                Intent otpVerificationIntent = new Intent(DeliveryActivity.this, OTPVerificationActivity.class);
                otpVerificationIntent.putExtra("mobileNumber", DataBaseQueries.addressesModalList.get(DataBaseQueries.selectedAddress).getAddressMobileNumber());
                startActivity(otpVerificationIntent);
            }
        });

    }

    public void startPayment() {
        Checkout checkout = new Checkout();

        checkout.setImage(R.mipmap.launchermain);

        final Activity activity = this;

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Yourcart");
            options.put("description", "Payment for Something");
            options.put("send_sms_hash", true);
            options.put("allow_rotation", false);

            int paymentAmount = Integer.parseInt(cartTotalAmount.getText().toString().substring(4, cartTotalAmount.getText().length() - 2)) * 100;

            options.put("currency", "INR");
            options.put("amount", paymentAmount);

            JSONObject prefill = new JSONObject();
            prefill.put("email", " ");
            prefill.put("contact", " ");

            options.put("prefill", prefill);

            checkout.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (DataBaseQueries.addressesModalList.get(DataBaseQueries.selectedAddress).getAddressAlternateMobileNumber() == "") {
            fullName.setText(DataBaseQueries.addressesModalList.get(DataBaseQueries.selectedAddress).getAddressFullname() + " - " + DataBaseQueries.addressesModalList.get(DataBaseQueries.selectedAddress).getAddressMobileNumber());
        } else {
            fullName.setText(DataBaseQueries.addressesModalList.get(DataBaseQueries.selectedAddress).getAddressFullname() + " - " + DataBaseQueries.addressesModalList.get(DataBaseQueries.selectedAddress).getAddressMobileNumber() + " or " + DataBaseQueries.addressesModalList.get(DataBaseQueries.selectedAddress).getAddressAlternateMobileNumber());
        }
        fullAddress.setText(DataBaseQueries.addressesModalList.get(DataBaseQueries.selectedAddress).getAddress());
        pincode.setText(DataBaseQueries.addressesModalList.get(DataBaseQueries.selectedAddress).getPincode());

        if (codOrderConfirmed) {
            showConfirmationLayout();
        }
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

    @Override
    public void onPaymentSuccess(String s) {
        showConfirmationLayout();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Error: " + s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        if (successResponse) {
            finish();
            return;
        }
        super.onBackPressed();
    }

    private void showConfirmationLayout() {
        successResponse = true;
        codOrderConfirmed = false;

        if (MainActivity.mainActivity != null){
            MainActivity.mainActivity.finish();
            MainActivity.mainActivity = null;
            MainActivity.showCart = false;
        }

        if (ProductDetailsActivity.productDetailsActivity != null){
            ProductDetailsActivity.productDetailsActivity.finish();
            ProductDetailsActivity.productDetailsActivity = null;
        }

        if (fromCart) {
            loadingDialog.show();
            Map<String, Object> updateCartlist = new HashMap<>();
            long cartListSize = 0;
            final List<Integer> indexList = new ArrayList<>();

            for (int x = 0; x < DataBaseQueries.cartlist.size(); x++) {
                if (!cartItemModalList.get(x).isInStock()) {
                    updateCartlist.put("productID"+cartListSize, cartItemModalList.get(x).getProductID());
                    cartListSize++;
                } else {
                    indexList.add(x);
                }
            }
            updateCartlist.put("listSize", cartListSize);

            FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("CART_DATA")
                    .set(updateCartlist).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                for (int x = 0; x < indexList.size(); x++) {
                                    DataBaseQueries.cartlist.remove(indexList.get(x).intValue());
                                    DataBaseQueries.cartItemModalList.remove(indexList.get(x).intValue());
                                    DataBaseQueries.cartItemModalList.remove(DataBaseQueries.cartItemModalList.size()-1);
                                }
                            } else {
                                String error = task.getException().getMessage();
                                Toast.makeText(DeliveryActivity.this, error, Toast.LENGTH_SHORT).show();
                            }
                            loadingDialog.dismiss();
                        }
                    });
        }

        deliveryContinueButton.setEnabled(false);
        changeOrAddNewAddressButton.setEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        orderIDTextView.setText("Order ID: " + orderID);
        orderConfirmationLayout.setVisibility(View.VISIBLE);
        continueShoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}