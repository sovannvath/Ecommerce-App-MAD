package com.example.ecommerce_mad_web_development;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Button loginBtn, registerBtn;
    LinearLayout navHome, navMessage, navCart, navProfile;
    FloatingActionButton navAddButton;
    LinearLayout categoryAll, categoryPhones, categoryCars, categoryVehicles,
            categoryClothes, categoryJobs, categoryPets;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        initializeViews();
        setupClickListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkAuthState();
    }

    private void initializeViews() {
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);
        navHome = findViewById(R.id.navHome);
        navMessage = findViewById(R.id.navMessage);
        navCart = findViewById(R.id.navCart);
        navProfile = findViewById(R.id.navProfile);
        navAddButton = findViewById(R.id.navAddButton);
        categoryAll = findViewById(R.id.categoryAll);
        categoryPhones = findViewById(R.id.categoryPhones);
        categoryCars = findViewById(R.id.categoryCars);
        categoryVehicles = findViewById(R.id.categoryVehicles);
        categoryClothes = findViewById(R.id.categoryClothes);
        categoryJobs = findViewById(R.id.categoryJobs);
        categoryPets = findViewById(R.id.categoryPets);
    }

    private void setupClickListeners() {
        loginBtn.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));
        registerBtn.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));

        navMessage.setOnClickListener(v -> startActivity(new Intent(this, MessageActivity.class)));
        navCart.setOnClickListener(v -> startActivity(new Intent(this, CartActivity.class)));
        navProfile.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));
        navAddButton.setOnClickListener(v -> startActivity(new Intent(this, AddItemActivity.class)));

        categoryAll.setOnClickListener(v -> startActivity(new Intent(this, AllCategoryActivity.class)));
        categoryPhones.setOnClickListener(v -> startActivity(new Intent(this, PhonesTabletsActivity.class)));
        categoryCars.setOnClickListener(v -> startActivity(new Intent(this, CarsActivity.class)));
        categoryVehicles.setOnClickListener(v -> startActivity(new Intent(this, VehiclesActivity.class)));
        categoryClothes.setOnClickListener(v -> startActivity(new Intent(this, ClothesActivity.class)));
        categoryJobs.setOnClickListener(v -> startActivity(new Intent(this, JobsActivity.class)));
        categoryPets.setOnClickListener(v -> startActivity(new Intent(this, PetsActivity.class)));
    }

    private void checkAuthState() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is signed in
            loginBtn.setVisibility(View.GONE);
            registerBtn.setVisibility(View.GONE);
            navAddButton.setVisibility(View.VISIBLE);
            checkUserRole(currentUser);
        } else {
            // User is signed out
            loginBtn.setVisibility(View.VISIBLE);
            registerBtn.setVisibility(View.VISIBLE);
            navAddButton.setVisibility(View.GONE);
        }
    }

    private void checkUserRole(FirebaseUser user) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Checking user role...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        db.collection("users").document(user.getUid()).get()
                .addOnCompleteListener(task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful() && task.getResult() != null && task.getResult().exists()) {
                        String role = task.getResult().getString("role");
                        if ("admin".equals(role)) {
                            startActivity(new Intent(this, AdminActivity.class));
                            finish();
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Log.e(TAG, "Role check failed", e);
                });
    }
}