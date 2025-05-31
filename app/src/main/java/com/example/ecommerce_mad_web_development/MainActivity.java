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
    Button loginBtn;
    Button registerBtn;

    // Bottom Navigation Buttons
    LinearLayout navHome, navMessage, navCart, navProfile;
    FloatingActionButton navAddButton;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase instances
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize UI components
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);

        // Bottom Navigation
        navHome = findViewById(R.id.navHome);
        navMessage = findViewById(R.id.navMessage);
        navCart = findViewById(R.id.navCart);
        navProfile = findViewById(R.id.navProfile);
        navAddButton = findViewById(R.id.navAddButton);

        // Check if user is logged in
        checkUserRoleAndNavigate();

        // 🔵 Login Button
        loginBtn.setOnClickListener(view -> {
            Log.d(TAG, "Login button clicked");
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });

        // 🔴 Register Button
        registerBtn.setOnClickListener(view -> {
            Log.d(TAG, "Register button clicked");
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
        });

        // Bottom Navigation Click Listeners
        navMessage.setOnClickListener(view -> {
            Log.d(TAG, "Messages button clicked");
            startActivity(new Intent(MainActivity.this, MessageActivity.class));
        });

        navCart.setOnClickListener(view -> {
            Log.d(TAG, "Cart button clicked");
            startActivity(new Intent(MainActivity.this, CartActivity.class));
        });

        navProfile.setOnClickListener(view -> {
            Log.d(TAG, "Profile button clicked");
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        });

        navAddButton.setOnClickListener(view -> {
            Log.d(TAG, "Add item button clicked");
            startActivity(new Intent(MainActivity.this, AddItemActivity.class));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: Checking user role");
        checkUserRoleAndNavigate();
    }

    private void checkUserRoleAndNavigate() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Checking authentication...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        Log.d(TAG, "Current user: " + (currentUser != null ? currentUser.getEmail() : "null"));

        if (currentUser != null) {
            // User is signed in, hide login/register buttons
            loginBtn.setVisibility(View.GONE);
            registerBtn.setVisibility(View.GONE);

            String uid = currentUser.getUid();
            Log.d(TAG, "Fetching user data for UID: " + uid);

            db.collection("users").document(uid).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        progressDialog.dismiss();
                        if (documentSnapshot.exists()) {
                            String role = documentSnapshot.getString("role");
                            Log.d(TAG, "User role: " + role);

                            if ("admin".equals(role)) {
                                Log.d(TAG, "Redirecting to AdminActivity");
                                Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                Log.d(TAG, "Regular user flow");
                                navAddButton.setVisibility(View.GONE);
                            }
                        } else {
                            Log.w(TAG, "No user data found in Firestore");
                            Toast.makeText(this, "No user data found", Toast.LENGTH_SHORT).show();
                            // Show login buttons if user document doesn't exist
                            loginBtn.setVisibility(View.VISIBLE);
                            registerBtn.setVisibility(View.VISIBLE);
                        }
                    })
                    .addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        Log.e(TAG, "Error fetching user role", e);
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        // Show login buttons on error
                        loginBtn.setVisibility(View.VISIBLE);
                        registerBtn.setVisibility(View.VISIBLE);
                    });
        } else {
            progressDialog.dismiss();
            Log.d(TAG, "No user signed in");
            // User is not signed in, show login/register buttons
            loginBtn.setVisibility(View.VISIBLE);
            registerBtn.setVisibility(View.VISIBLE);
            navAddButton.setVisibility(View.GONE);
        }
    }
}