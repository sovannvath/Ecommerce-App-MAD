package com.example.ecommerce_mad_web_development;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin, buttonBackToHome;
    private TextView textViewRegisterLink;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        // Check if user is already logged in
        if (mAuth.getCurrentUser() != null) {
            navigateToMain();
            return;
        }

        initializeViews();
        setupClickListeners();
    }

    private void initializeViews() {
        editTextEmail = findViewById(R.id.editText_email);
        editTextPassword = findViewById(R.id.editText_password);
        buttonLogin = findViewById(R.id.button_login);
        buttonBackToHome = findViewById(R.id.button_backToHome);
        textViewRegisterLink = findViewById(R.id.textView_registerLink);
    }

    private void setupClickListeners() {
        buttonLogin.setOnClickListener(v -> loginUser());
        textViewRegisterLink.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
        buttonBackToHome.setOnClickListener(v -> finish());
    }

    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        buttonLogin.setEnabled(false);
        Log.d(TAG, "Attempting login with email: " + email);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    buttonLogin.setEnabled(true);

                    if (task.isSuccessful()) {
                        Log.d(TAG, "Login successful");
                        navigateToMain();
                    } else {
                        String error = task.getException() != null ?
                                task.getException().getMessage() : "Login failed";
                        Log.e(TAG, "Login error: " + error);
                        Toast.makeText(this, "Login failed: " + error, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void navigateToMain() {
        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}