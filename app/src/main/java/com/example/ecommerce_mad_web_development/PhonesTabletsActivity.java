package com.example.ecommerce_mad_web_development;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class PhonesTabletsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phones_tablets);

        // Now you can use findViewById to access views inside product_cart.xml
        // Example:
        // TextView cartTitle = findViewById(R.id.cart_title);
    }
}
