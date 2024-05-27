package com.ecommerce.yourcart;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

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
//        String SMS_API = "https://www.fast2sms.com/dev/bulkV2";
//
//
//        String apiKey = "ukGy4N3mKzIalJpHrBT1WDnd05Ac6Uiw8o7ZbfLVst2QqMCPvgWZz46qBgyHrYUxkn307QPlA8uDJied";
//        String message = "Dear Customer,\nYour OTP Verification Code for Order Confirmation is: " + OTP;
//
//        try {
//            HttpResponse<String> response = Unirest.post("https://www.fast2sms.com/dev/bulkV2")
//                    .header("authorization", apiKey)
//                    .header("Content-Type", "application/x-www-form-urlencoded")
//                    .body("message=" + message + "&language=english&route=q&numbers=" + userMobileNumber)
//                    .asString();
//        } catch (UnirestException e) {
//            throw new RuntimeException(e);
//        }

        Toast.makeText(OTPVerificationActivity.this, "OTP: " + OTP, Toast.LENGTH_LONG).show();
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