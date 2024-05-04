package com.ecommerce.yourcart;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OTPVerificationActivity extends AppCompatActivity {
    private TextView phoneNumber;
    private EditText otpField;
    private Button verifyButton;

    private String userMobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);

        phoneNumber = findViewById(R.id.otp_verification_phone_number);
        otpField = findViewById(R.id.otp_field);
        verifyButton = findViewById(R.id.verification_button);

        userMobileNumber = getIntent().getStringExtra("mobileNumber");

        phoneNumber.setText("+91 " + userMobileNumber);

        Random random = new Random();
        int OTP = random.nextInt(999999 - 111111) + 111111;

        Toast.makeText(OTPVerificationActivity.this, String.valueOf(OTP), Toast.LENGTH_LONG).show();
        verifyButton.setEnabled(true);
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otpField.getText().toString().equals(String.valueOf(OTP))) {
                    DeliveryActivity.codOrderConfirmed = true;
                    finish();
                } else {
                    Toast.makeText(OTPVerificationActivity.this, "Incorrect OTP!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}