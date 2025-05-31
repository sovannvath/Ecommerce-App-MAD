package com.example.ecommerce_mad_web_development;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Get product data from intent
        Product product = getIntent().getParcelableExtra("product");

        // Initialize all views
        ImageView productImage = findViewById(R.id.detailProductImage);
        ImageView backButton = findViewById(R.id.backButton);
        TextView title = findViewById(R.id.detailProductTitle);
        TextView price = findViewById(R.id.detailProductPrice);
        TextView originalPrice = findViewById(R.id.detailOriginalPrice);
        TextView condition = findViewById(R.id.detailProductCondition);
        TextView description = findViewById(R.id.detailProductDescription);
        TextView sellerName = findViewById(R.id.sellerName);
        TextView reviewCount = findViewById(R.id.reviewCount);
        RatingBar ratingBar = findViewById(R.id.productRating);
        Button buyNowBtn = findViewById(R.id.buyNowDetailBtn);
        Button contactSellerBtn = findViewById(R.id.contactSellerBtn);

        // Set product data if available
        if (product != null) {
            // Load product image
            Glide.with(this)
                    .load(product.getImageUrl())
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(productImage);

            // Set text values
            title.setText(product.getTitle());
            price.setText(product.getPrice());
            originalPrice.setText(product.getOriginalPrice());
            condition.setText(product.getCondition());
            description.setText(product.getDescription());
            sellerName.setText(product.getSellerName());
            reviewCount.setText(String.format("(%d reviews)", product.getReviewCount()));
            ratingBar.setRating(product.getRating());

            // Set up click listeners
            buyNowBtn.setOnClickListener(v -> {
                Intent checkoutIntent = new Intent(this, CheckoutActivity.class);
                checkoutIntent.putExtra("product", product);
                startActivity(checkoutIntent);
            });

            contactSellerBtn.setOnClickListener(v -> {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + product.getSellerEmail()));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Inquiry about " + product.getTitle());
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Hello, I'm interested in your product: " + product.getTitle());
                startActivity(Intent.createChooser(emailIntent, "Send email via"));
            });
        }

        // Set up back button
        backButton.setOnClickListener(v -> finish());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Add custom transition if needed
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}