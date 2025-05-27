package com.example.ecommerce_mad_web_development;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CartActivity extends Activity {

    // Variables for items
    private TextView tvQuantity1, tvPrice1, tvQuantity2, tvPrice2, tvTotal;
    private int quantity1 = 1, quantity2 = 1;
    private final int price1 = 99, price2 = 89;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initializeViews();
        setupQuantityControls();
        setupCheckoutButton();
        setupBottomNavigation();
        updateAllViews();
    }

    private void initializeViews() {
        tvQuantity1 = findViewById(R.id.tvQuantity1);
        tvPrice1 = findViewById(R.id.tvPrice1);
        tvQuantity2 = findViewById(R.id.tvQuantity2);
        tvPrice2 = findViewById(R.id.tvPrice2);
        tvTotal = findViewById(R.id.tvTotal);
    }

    private void setupQuantityControls() {
        // Item 1 controls
        findViewById(R.id.btnDecrease1).setOnClickListener(v -> adjustQuantity(-1, 1));
        findViewById(R.id.btnIncrease1).setOnClickListener(v -> adjustQuantity(1, 1));

        // Item 2 controls
        findViewById(R.id.btnDecrease2).setOnClickListener(v -> adjustQuantity(-1, 2));
        findViewById(R.id.btnIncrease2).setOnClickListener(v -> adjustQuantity(1, 2));
    }

    private void setupCheckoutButton() {
        Button btnCheckout = findViewById(R.id.btnCheckout);
        btnCheckout.setOnClickListener(v -> {
            Toast.makeText(this, "Proceeding to checkout", Toast.LENGTH_SHORT).show();
            // Start CheckoutActivity if you have one
            // startActivity(new Intent(this, CheckoutActivity.class));
        });
    }

    private void setupBottomNavigation() {
        // Home button - goes to MainActivity
        findViewById(R.id.navHome).setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
        });

        // Message button - goes to MessageActivity
        findViewById(R.id.navMessage).setOnClickListener(v -> {
            startActivity(new Intent(this, MessageActivity.class));
            finish();
        });

        // Card button - goes to AddItemActivity (assuming this is for adding payment cards)
        findViewById(R.id.navCard).setOnClickListener(v -> {
            startActivity(new Intent(this, AddItemActivity.class));
            finish();
        });

        // Profile button - goes to ProfileActivity
        findViewById(R.id.navProfile).setOnClickListener(v -> {
            startActivity(new Intent(this, ProfileActivity.class));
            finish();
        });
    }

    private void adjustQuantity(int change, int itemNumber) {
        switch (itemNumber) {
            case 1:
                quantity1 = Math.max(1, quantity1 + change);
                updateItem1();
                break;
            case 2:
                quantity2 = Math.max(1, quantity2 + change);
                updateItem2();
                break;
        }
        updateTotal();
    }

    private void updateItem1() {
        tvQuantity1.setText(String.valueOf(quantity1));
        tvPrice1.setText(String.format("$%d", quantity1 * price1));
    }

    private void updateItem2() {
        tvQuantity2.setText(String.valueOf(quantity2));
        tvPrice2.setText(String.format("$%d", quantity2 * price2));
    }

    private void updateTotal() {
        int total = (quantity1 * price1) + (quantity2 * price2);
        tvTotal.setText(String.format("$%d", total));
    }

    private void updateAllViews() {
        updateItem1();
        updateItem2();
        updateTotal();
    }
}
