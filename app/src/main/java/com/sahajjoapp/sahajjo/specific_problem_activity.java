package com.sahajjoapp.sahajjo;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.HashMap;
import java.util.Map;

public class specific_problem_activity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    DrawerLayout drawerLayout;
    TextView userName, userEmail, userMobile, userLocation;
    EditText problemTitle, problemDescription, problemLocation;
    Button submitButton;
    String title,description,location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_specific_problem);
        drawerLayout = findViewById(R.id.specific_problem_drawer_layout);

        NavigationView navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        userName = headerView.findViewById(R.id.userName);
        userEmail = headerView.findViewById(R.id.userEmail);
        userMobile = headerView.findViewById(R.id.userMobile);
        userLocation = headerView.findViewById(R.id.userLocation);
        //*************************************************************************************************
        problemTitle = findViewById(R.id.problemTitle);
        problemDescription = findViewById(R.id.problemDetails);
        problemLocation = findViewById(R.id.problemLocation);
        submitButton = findViewById(R.id.submitProblembtn);


        // Fetch user data from FireStore and set it in the drawer
        fetchUserData();

        //*************************Code for this Activity***********************************************
        submitButton.setOnClickListener(view -> {

            title = problemTitle.getText().toString();
            description = problemDescription.getText().toString();
            location = problemLocation.getText().toString();



            if (title.isEmpty()) {
                problemTitle.setError("Title is required");
                problemTitle.requestFocus();
                return;

            }
            if (description.isEmpty()) {
                problemDescription.setError("Description is required");
                problemDescription.requestFocus();
                return;
            }
            if (location.isEmpty()) {
                problemLocation.setError("Location is required");
                problemLocation.requestFocus();
                return;
            }

            // Get the current logged-in user
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {

                Map<String, Object> problemData = new HashMap<>();
                problemData.put("problemTitle", title);
                problemData.put("problemDescription", description);
                problemData.put("problemLocation", location);
                problemData.put("userId", currentUser.getUid());

                // Add the data to the "problems" collection for this user
                firestore.collection("problems")
                        .add(problemData)
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(this, "Problem submitted!", Toast.LENGTH_SHORT).show();
                        });


                Intent intent = new Intent(specific_problem_activity.this, Person_Main_Interface_Activity.class);
                startActivity(intent);
                finish();

            }



        });

    }


    private void fetchUserData() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            firestore.collection("users").document(user.getUid()).get(Source.SERVER)
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            Map<String, Object> data = documentSnapshot.getData();
                            if (data != null) {
                                userName.setText("Name: " + (String) data.get("username"));
                                userEmail.setText("Email: " + (String) data.get("email"));
                                userMobile.setText("Mobile: " + (String) data.get("mobile"));
                                userLocation.setText("Location: " + (String) data.get("location"));

                            }
                        }
                    });

        }
    }

    public void openDrawer(View view) {
        drawerLayout.openDrawer(findViewById(R.id.navigation_view));
    }
    public void logOut(MenuItem menuItem){
        mAuth.signOut();
        Intent intent = new Intent(specific_problem_activity.this, MainActivity.class);
        startActivity(intent);
    }

}