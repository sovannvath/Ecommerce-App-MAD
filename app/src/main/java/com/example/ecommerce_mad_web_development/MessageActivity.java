package com.example.ecommerce_mad_web_development;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MessageActivity extends Activity {

    private TextView tabMessage, tabComment, noResultText;
    private LinearLayout navHome, navMessage, navCard, navProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        // Initialize tabs and no result text
        tabMessage = findViewById(R.id.tabMessage);
        tabComment = findViewById(R.id.tabComment);
        noResultText = findViewById(R.id.noResultText);

        // Initialize bottom navigation layout items
        navHome = findViewById(R.id.navHome);
        navMessage = findViewById(R.id.navMessage);
        navCard = findViewById(R.id.navCard);
        navProfile = findViewById(R.id.navProfile);

        // Default selected tab
        highlightSelectedTab(tabMessage, tabComment);
        noResultText.setText("No Message!!");

        // Tab click listeners
        tabMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightSelectedTab(tabMessage, tabComment);
                noResultText.setText("No Message!!");
            }
        });

        tabComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightSelectedTab(tabComment, tabMessage);
                noResultText.setText("No Comment!!");
            }
        });

        // Bottom navigation click listeners
        navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessageActivity.this, MainActivity.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });

        navMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // You are already in MessageActivity, so do nothing or refresh
            }
        });

        navCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessageActivity.this, CartActivity.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });

        navProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessageActivity.this, ProfileActivity.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });
    }

    // Highlight selected tab
    private void highlightSelectedTab(TextView selected, TextView unselected) {
        selected.setBackgroundColor(0xFFD0D0D0);   // selected tab background
        unselected.setBackgroundColor(0xFFE0E0E0); // unselected tab background
    }
}
