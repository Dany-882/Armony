package com.example.perfildealumno;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import android.view.View;



public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private EditText etUsername;
    private Button btnContinue;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        etUsername = findViewById(R.id.et_username);
        btnContinue = findViewById(R.id.btn_continue);

        // Botón continuar
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = etUsername.getText().toString().trim();

                if (nombre.isEmpty()) {
                    etUsername.setError("Ingresa un nombre");
                    return;
                }

                // 💛 Guardar el nombre en SharedPreferences
                UserPrefs prefs = new UserPrefs(MainActivity.this);
                prefs.saveString("username", nombre);

                // Enviar a Dashboard
                Intent i = new Intent(MainActivity.this, DashboardActivity.class);
                i.putExtra("username", nombre);
                startActivity(i);
            }
        });

        // Menu lateral
        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_inicio) {
                String nombre = etUsername.getText().toString().trim();
                if (nombre.isEmpty()) {
                    etUsername.setError("Ingresa un nombre para ir al Dashboard");
                } else {
                    Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                    intent.putExtra("username", nombre);
                    startActivity(intent);
                }
            }
            drawerLayout.closeDrawers();
            return true;
        });
    }
}
