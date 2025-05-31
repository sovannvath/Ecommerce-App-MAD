package com.example.ecommerce_mad_web_development;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Button btnManageProducts = findViewById(R.id.btnManageProducts);
        Button btnManageUsers = findViewById(R.id.btnManageUsers);

        btnManageProducts.setOnClickListener(v -> {
            // Start product management activity
            Intent intent = new Intent(AdminActivity.this, AddItemActivity.class); // Using existing AddItemActivity
            startActivity(intent);
        });

        btnManageUsers.setOnClickListener(v -> {
            // Start user management activity
            // Create this activity if needed, or use existing ProfileActivity
            Intent intent = new Intent(AdminActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed(); // Added super call
        moveTaskToBack(true); // Minimize app instead of going back
    }
}