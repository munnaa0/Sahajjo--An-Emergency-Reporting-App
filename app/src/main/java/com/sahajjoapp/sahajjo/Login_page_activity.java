package com.sahajjoapp.sahajjo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log; // Import for logging
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.Map;

public class Login_page_activity extends AppCompatActivity {
    EditText email, password;
    TextView textView;
    Button loginButton;
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    String typeUser;
    private static final String TAG = "LoginActivity"; // For logging

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        textView = findViewById(R.id.RegisterIntent);
        email = findViewById(R.id.EmailInputLogin);
        password = findViewById(R.id.PasswordInput);
        loginButton = findViewById(R.id.loginbutton);

        textView.setOnClickListener(view -> {
            Intent intent = new Intent(Login_page_activity.this, person_register_activity.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailValue = email.getText().toString();
                String passwordValue = password.getText().toString();

                if (emailValue.isEmpty()) {
                    email.setError("Email cannot be empty");
                    return;
                }
                if (passwordValue.isEmpty()) {
                    password.setError("Password cannot be empty");
                    return;
                }

                Log.d(TAG, "Attempting to sign in with email: " + emailValue);

                mAuth.signInWithEmailAndPassword(emailValue, passwordValue)
                        .addOnCompleteListener(Login_page_activity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Sign in successful");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user != null) {
                                        firestore.collection("users").document(user.getUid()).get(Source.SERVER)
                                                .addOnSuccessListener(documentSnapshot -> {
                                                    if (documentSnapshot.exists()) {
                                                        Map<String, Object> data = documentSnapshot.getData();
                                                        if (data != null) {
                                                            typeUser = (String) data.get("role");

                                                            // Check the role and navigate to the corresponding activity
                                                            Intent intent;
                                                            if ("Admin".equals(typeUser)) {
                                                                intent = new Intent(Login_page_activity.this, Admin_main_Interface_Activity.class);
                                                                Toast.makeText(Login_page_activity.this, "Admin Login Successful", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                intent = new Intent(Login_page_activity.this, Person_Main_Interface_Activity.class);
                                                                Toast.makeText(Login_page_activity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                                            }
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                    }
                                                });
                                    }
                                } else {
                                    Log.e(TAG, "Sign in failed", task.getException()); // Log the exception
                                    String errorMessage = "Login failed. Please try again.";
                                    if (task.getException() instanceof FirebaseAuthException) {
                                        FirebaseAuthException e = (FirebaseAuthException) task.getException();
                                        String errorCode = e.getErrorCode();
                                        switch (errorCode) {
                                            case "ERROR_INVALID_EMAIL":
                                                errorMessage = "Invalid email format.";
                                                break;
                                            case "ERROR_WRONG_PASSWORD":
                                                errorMessage = "Incorrect password.";
                                                break;
                                            case "ERROR_USER_NOT_FOUND":
                                                errorMessage = "No user found with this email.";
                                                break;
                                            case "ERROR_USER_DISABLED":
                                                errorMessage = "User account is disabled.";
                                                break;
                                            case "ERROR_OPERATION_NOT_ALLOWED":
                                                errorMessage = "Email/password accounts are not enabled.";
                                                break;
                                            default:
                                                errorMessage = "Error: " + errorCode; // Display error code
                                        }
                                    }
                                    Toast.makeText(Login_page_activity.this, errorMessage, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
