package com.example.perfildealumno;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    Button btnVisitPet;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // RECIBIR userId
        userId = getIntent().getStringExtra("userId");

        // 🔥 USAR TUS IDS EXACTOS DEL XML 🔥
        drawerLayout = findViewById(R.id.dashboard_drawer);
        navigationView = findViewById(R.id.dashboard_navigation_view);
        toolbar = findViewById(R.id.dashboard_toolbar);
        btnVisitPet = findViewById(R.id.btn_visit_pet);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // ------------ NAVIGATION VIEW -------------
        navigationView.setNavigationItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_mascota) {
                Intent i = new Intent(DashboardActivity.this, PetActivity.class);
                i.putExtra("userId", userId);
                startActivity(i);
            }
            if (id == R.id.nav_config) {
                Intent i = new Intent(DashboardActivity.this, SettingsActivity.class);
                i.putExtra("userId", userId);
                startActivity(i);
            }
            if (id == R.id.nav_logout) { // Asume que este es el ID de tu botón de cerrar sesión
                // Cerrar sesión en Firebase
                FirebaseAuth.getInstance().signOut();

                // Volver a MainActivity
                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Evita que el usuario pueda volver con "atrás"
                startActivity(intent);
                finish(); // Cierra DashboardActivity
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        // ------------ BOTÓN "VISITAR MASCOTA" -------------
        btnVisitPet.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, PetActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        });

    }





    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}