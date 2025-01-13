package com.sahajjoapp.sahajjo;

import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.Map;

public class splash_screen_activity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    String typeUser;

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // User is logged in, retrieve their role from FireStore
            firestore.collection("users").document(user.getUid()).get(Source.SERVER)
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            Map<String, Object> data = documentSnapshot.getData();
                            if (data != null) {
                                typeUser = (String) data.get("role");

                                // Check the role and navigate to the corresponding activity
                                Intent intent;
                                if ("Admin".equals(typeUser)) {
                                    intent = new Intent(splash_screen_activity.this, Admin_main_Interface_Activity.class);
                                } else {
                                    intent = new Intent(splash_screen_activity.this, Person_Main_Interface_Activity.class);
                                }
                                startActivity(intent);
                                finish();
                            }
                        }
                    })
                    .addOnFailureListener(e -> {

                    });
        } else {

            HomepageShow();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        //direct();

    }

    private void direct(){
        Intent intent  = new Intent(splash_screen_activity.this, Person_Main_Interface_Activity.class);
        startActivity(intent);
        finish();
    }

    private void HomepageShow() {
        // Delayed navigation to MainActivity (only if no user is logged in)
        new Handler().postDelayed(() -> {
                startActivity(new Intent(splash_screen_activity.this, MainActivity.class));
                finish();
        }, 2000); // 2 seconds delay
    }
}
