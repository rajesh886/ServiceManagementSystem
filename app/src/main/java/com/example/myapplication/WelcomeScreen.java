package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeScreen extends AppCompatActivity {

    Button customer;
    Button vendor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);


        customer = (Button) findViewById(R.id.customer);
        vendor = (Button) findViewById(R.id.vendor);



        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intToHome = new Intent(WelcomeScreen.this, UserLogin.class);
                startActivity(intToHome);
            }
        });

        vendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vendorlogin = new Intent(WelcomeScreen.this, VendorLogin.class);
                startActivity(vendorlogin);
            }
        });

    }

}
