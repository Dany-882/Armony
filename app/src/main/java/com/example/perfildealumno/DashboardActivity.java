package com.example.perfildealumno;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;


import com.google.android.material.navigation.NavigationView;

public class DashboardActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;

    private TextView tvWelcome, tvSubtitle;
    private TextView tvClases, tvPuntos, tvJuegos, tvRacha;
    private ImageView ivPet;
    private Button btnVisitPet;
    private ProgressBar pbEnergy, pbTime, pbTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.dashboard_toolbar);
        setSupportActionBar(toolbar);

        // Drawer y NavigationView
        drawerLayout = findViewById(R.id.dashboard_drawer);
        navigationView = findViewById(R.id.dashboard_navigation_view);

        toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.open,
                R.string.close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Vistas del Dashboard
        tvWelcome = findViewById(R.id.tv_welcome);
        tvSubtitle = findViewById(R.id.tv_subtitle);

        tvClases = findViewById(R.id.tv_clases);
        tvPuntos = findViewById(R.id.tv_puntos);
        tvJuegos = findViewById(R.id.tv_juegos);
        tvRacha = findViewById(R.id.tv_racha);

        ivPet = findViewById(R.id.iv_pet);
        btnVisitPet = findViewById(R.id.btn_visit_pet);

        pbEnergy = findViewById(R.id.pb_energy);
        pbTime = findViewById(R.id.pb_time);
        pbTasks = findViewById(R.id.pb_tasks);

        // --- PASO D: Recuperar nombre desde SharedPreferences ---
        UserPrefs prefs = new UserPrefs(this);
        String nombre = prefs.getString("username", "Usuario");

        tvWelcome.setText("¡Bienvenid@ " + nombre + "!");
        tvSubtitle.setText("Continúa tu aventura de aprendizaje");

        // Datos iniciales
        tvClases.setText("3");
        tvPuntos.setText("1250");
        tvJuegos.setText("8");
        tvRacha.setText("7");

        pbEnergy.setProgress(85);
        pbTime.setProgress(60);
        pbTasks.setProgress(80);

        // Botón de visitar mascota (vacío por ahora)
        btnVisitPet.setOnClickListener(v -> {
            // Aquí agregas la pantalla de mascota después
        });

        // Menú lateral: solo funciona Dashboard por ahora
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();

            if (id == R.id.nav_inicio) {
                drawerLayout.closeDrawers();
            } else {
                drawerLayout.closeDrawers();
            }

            return true;
        });
    }
}

