package com.sahajjoapp.sahajjo;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.Map;

public class Person_Main_Interface_Activity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    DrawerLayout drawerLayout;
    TextView userName, userEmail, userMobile, userLocation;
    Button emergencyButton, specificProblemButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_main_interface);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        drawerLayout = findViewById(R.id.drawer_layout);

        emergencyButton = findViewById(R.id.emergencyButton);
        specificProblemButton = findViewById(R.id.specificProblemsButton);

        NavigationView navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        userName = headerView.findViewById(R.id.userName);
        userEmail = headerView.findViewById(R.id.userEmail);
        userMobile = headerView.findViewById(R.id.userMobile);
        userLocation = headerView.findViewById(R.id.userLocation);


        specificProblemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Person_Main_Interface_Activity.this, specific_problem_activity.class);
                startActivity(intent);
                finish();
            }
        });


        // Fetch user data from FireStore and set it in the drawer
        fetchUserData();

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

    public void logOut(MenuItem menuItem) {
        mAuth.signOut();
        Intent intent = new Intent(Person_Main_Interface_Activity.this, MainActivity.class);
        startActivity(intent);
    }
}
