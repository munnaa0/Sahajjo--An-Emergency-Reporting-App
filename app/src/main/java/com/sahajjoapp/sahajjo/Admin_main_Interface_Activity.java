package com.sahajjoapp.sahajjo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.Map;

public class Admin_main_Interface_Activity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    DrawerLayout drawerLayout;
    TextView userName, userEmail, userMobile, userLocation, userNid, userOfficeId;
    RecyclerView recyclerViewProblems;
    problemAdapter problemAdapter;
    ArrayList<Problem> problemArrayList;

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

        // Fetch user data from FireStore and set it in the drawer
        fetchUserData();

        recyclerViewProblems = findViewById(R.id.recyclerViewProblems);
        recyclerViewProblems.setLayoutManager(new LinearLayoutManager(this));
        problemArrayList = new ArrayList<>();
        problemAdapter = new problemAdapter(this, problemArrayList);
        recyclerViewProblems.setAdapter(problemAdapter);

        // Fetch problems with real-time updates
        fetchProblems();
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

    private void fetchProblems() {
        firestore.collection("problems")
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        Toast.makeText(Admin_main_Interface_Activity.this, "Error fetching problems: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (queryDocumentSnapshots != null) {
                        problemArrayList.clear(); // Clear existing data to avoid duplicates
                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            Problem problem = document.toObject(Problem.class);
                            if (problem != null) {
                                problemArrayList.add(problem);
                            }
                        }
                        problemAdapter.notifyDataSetChanged(); // Notify the adapter of the data change
                    }
                });
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
