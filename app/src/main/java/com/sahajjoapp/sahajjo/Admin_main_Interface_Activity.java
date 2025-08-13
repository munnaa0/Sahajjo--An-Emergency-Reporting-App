package com.sahajjoapp.sahajjo;

import android.annotation.SuppressLint;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.Map;

public class Admin_main_Interface_Activity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    DrawerLayout drawerLayout;
    TextView userName, userEmail, userMobile, userLocation, userNid, userOfficeId;
    Button reportedProblemsButton, emergencySituationButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_interface);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);

        userName = headerView.findViewById(R.id.userName);
        userEmail = headerView.findViewById(R.id.userEmail);
        userMobile = headerView.findViewById(R.id.userMobile);
        userLocation = headerView.findViewById(R.id.userLocation);
        userNid = headerView.findViewById(R.id.userNid);
        userOfficeId = headerView.findViewById(R.id.userOfficeId);

        reportedProblemsButton = findViewById(R.id.reportedProblemsButton);
        emergencySituationButton = findViewById(R.id.emergencySituationButton);

        // Fetch user data from FireStore and set it in the drawer
        fetchUserData();

        reportedProblemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_main_Interface_Activity.this, Admin_Reported_Problems_Activity.class);
                startActivity(intent);
            }
        });

        emergencySituationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implement emergency situation functionality
                // For now, this button doesn't do anything as requested
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
                                userLocation.setText("Office location: " + (String) data.get("Office location"));
                                userNid.setText("Nid: " + (String) data.get("Nid Number"));
                                userOfficeId.setText("Office Id: " + (String) data.get("office Id Number"));
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
        Intent intent = new Intent(Admin_main_Interface_Activity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
