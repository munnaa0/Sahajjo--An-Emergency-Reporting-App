package com.sahajjoapp.sahajjo;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.Permission;

public class MainActivity extends AppCompatActivity {
    Button btnPerson;
    Button btnAuthor;
    Button btnLogin;
    FirebaseAuth mAuth;

    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth = FirebaseAuth.getInstance();

        btnPerson = findViewById(R.id.PersonButton);
        btnAuthor = findViewById(R.id.AuthorButton);
        btnLogin = findViewById(R.id.LoginButton);



        location = new Location(LocationManager.GPS_PROVIDER);


        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        float speed = location.getSpeed();




        btnLogin.setOnClickListener(view -> {
            Intent main_login = new Intent(MainActivity.this, Login_page_activity.class);
            startActivity(main_login);
        });

        btnPerson.setOnClickListener(view -> {
            Intent personChange = new Intent(MainActivity.this, person_register_activity.class);
            startActivity(personChange);
        });

        btnAuthor.setOnClickListener(view -> {
            // Uncomment to enable Author Registration
          Intent AuthorRegPage = new Intent(MainActivity.this, Admin_Register_Activity.class);
          startActivity(AuthorRegPage);
        });
    }
}
