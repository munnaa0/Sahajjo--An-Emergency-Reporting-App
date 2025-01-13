package com.sahajjoapp.sahajjo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class Admin_Register_Activity extends AppCompatActivity {

    EditText username, email, officeLocation, mobile, firstPassword, confirmPassword,nidNum,officeIdNum;
    Button submitButton;
    FirebaseAuth mAuth;
    private static final String TAG = "RegisterActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);
        mAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id.username_input);
        email = findViewById(R.id.email_input);
        officeLocation = findViewById(R.id.location_input);
        mobile = findViewById(R.id.mobile_input);
        firstPassword = findViewById(R.id.first_password);
        confirmPassword = findViewById(R.id.confirm_password);
        submitButton = findViewById(R.id.Submit_Button);
        officeIdNum = findViewById(R.id.jobIdInput);
        nidNum = findViewById(R.id.NidInput);

        submitButton.setOnClickListener(view -> {
            String usernameValue = username.getText().toString();
            String emailValue = email.getText().toString();
            String locationValue = officeLocation.getText().toString();
            String mobileValue = mobile.getText().toString();
            String firstPasswordValue = firstPassword.getText().toString();
            String confirmPasswordValue = confirmPassword.getText().toString();
            String officeIdValue = officeIdNum.getText().toString();
            String nidValue = nidNum.getText().toString();
            String role = "Admin";

            boolean isValid = validateFields(usernameValue, emailValue, locationValue, mobileValue, firstPasswordValue, confirmPasswordValue, officeIdValue, nidValue);
            if (isValid) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                mAuth.createUserWithEmailAndPassword(emailValue, firstPasswordValue)
                        .addOnCompleteListener(Admin_Register_Activity.this, task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                assert firebaseUser != null;
                                String userId = firebaseUser.getUid();
                                Map<String, Object> user = new HashMap<>();
                                user.put("email", emailValue);
                                user.put("username", usernameValue);
                                user.put("Office location", locationValue);
                                user.put("mobile", mobileValue);
                                user.put("Nid Number", nidValue);
                                user.put("office Id Number", officeIdValue);
                                user.put("role", role);
                                db.collection("users").document(userId)
                                        .set(user)
                                        .addOnSuccessListener(aVoid -> {
                                            Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                                            Log.d(TAG, "User data saved successfully with role: normal");

                                            // Sign out the user after registration
                                            mAuth.signOut();

                                            // Redirect to the login activity
                                            Intent intent = new Intent(Admin_Register_Activity.this, Login_page_activity.class);
                                            startActivity(intent);
                                            finish();
                                        })
                                        .addOnFailureListener(e -> {
                                            Log.e(TAG, "Error saving user data: " + e.getMessage());
                                            Toast.makeText(getApplicationContext(), "Failed to save user data. Please try again.", Toast.LENGTH_SHORT).show();
                                        });
                            } else {
                                Log.e(TAG, "Authentication failed: " + Objects.requireNonNull(task.getException()).getMessage());
                                Toast.makeText(Admin_Register_Activity.this, "Authentication failed. " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });



    }

    private boolean validateFields(String username, String email, String location, String mobile, String password, String confirmPassword, String officeId, String nid) {
        boolean isValid = true;
        if (username.isEmpty()) {
            this.username.setError("Name cannot be empty");
            isValid = false;
        }
        if (email.isEmpty()) {
            this.email.setError("Email cannot be empty");
            isValid = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            this.email.setError("Please enter a valid email address");
            isValid = false;
        }
        if (location.isEmpty()) {
            this.officeLocation.setError("Location cannot be empty");
            isValid = false;
        }
        if (officeId.isEmpty()) {
            this.officeIdNum.setError("Office Id cannot be empty");
            isValid = false;
        }
        if (nid.isEmpty()) {
            this.nidNum.setError("Nid cannot be empty");
            isValid = false;
        }
        if (mobile.isEmpty()) {
            this.mobile.setError("Mobile number cannot be empty");
            isValid = false;
        } else if (mobile.length() < 11) {
            this.mobile.setError("Mobile number must be at least 11 digits long");
            isValid = false;
        }
        if (password.isEmpty()) {
            this.firstPassword.setError("Password cannot be empty");
            isValid = false;
        } else if (password.length() < 8) {
            this.firstPassword.setError("Password must be at least 8 characters long");
            isValid = false;
        }
        if (confirmPassword.isEmpty()) {
            this.confirmPassword.setError("Confirm Password cannot be empty");
            isValid = false;
        } else if (!password.equals(confirmPassword)) {
            this.confirmPassword.setError("Passwords do not match");
            isValid = false;
        }

        return isValid;
    }

}