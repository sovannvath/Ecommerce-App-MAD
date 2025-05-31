package com.example.ecommerce_mad_web_development;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin, buttonBackToHome;
    private TextView textViewRegisterLink;

    private FirebaseAuth mAuth;
    private static final String TAG = "LoginActivity";

    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_LOGGED_IN = "isLoggedIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d(TAG, "onCreate: Initializing login screen");

        mAuth = FirebaseAuth.getInstance();

        // Bind views
        editTextEmail = findViewById(R.id.editText_email);
        editTextPassword = findViewById(R.id.editText_password);
        buttonLogin = findViewById(R.id.button_login);
        buttonBackToHome = findViewById(R.id.button_backToHome);
        textViewRegisterLink = findViewById(R.id.textView_registerLink);

        // Login button click
        buttonLogin.setOnClickListener(v -> {
            Log.d(TAG, "Login button clicked");
            loginUser();
        });

        // Register link click
        textViewRegisterLink.setOnClickListener(v -> {
            Log.d(TAG, "Register link clicked");
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        // Back to Home click
        buttonBackToHome.setOnClickListener(v -> {
            Log.d(TAG, "Back to Home button clicked");
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        });
    }

    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        buttonLogin.setEnabled(false); // Prevent multiple taps
        Log.d(TAG, "Attempting login with email: " + email);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    buttonLogin.setEnabled(true);

                    if (task.isSuccessful()) {
                        Log.i(TAG, "Login successful!");

                        // Save login state
                        getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                                .edit()
                                .putBoolean(KEY_LOGGED_IN, true)
                                .apply();

                        Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();

                        // Navigate to MainActivity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        String error = (task.getException() != null)
                                ? task.getException().getMessage()
                                : "Login failed";
                        Log.e(TAG, "Login failed: " + error);
                        Toast.makeText(LoginActivity.this, "Login failed: " + error, Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(e -> {
                    buttonLogin.setEnabled(true);
                    Log.e(TAG, "Firebase login error: " + e.getMessage());
                    Toast.makeText(LoginActivity.this, "Firebase error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: Checking login state...");

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean(KEY_LOGGED_IN, false);

        if (isLoggedIn && mAuth.getCurrentUser() != null) {
            Log.i(TAG, "User already logged in");
            Toast.makeText(this, "Welcome back!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }
}
