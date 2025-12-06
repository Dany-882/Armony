package com.example.perfildealumno;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // --------- Verificar si hay usuario logueado -----------
        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            // Usuario ya logueado, ir a SettingsActivity o tu pantalla principal
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            finish(); // cerrar MainActivity para que no vuelva atrás
            return; // no continuar con onCreate
        }
        // --------------------------------------------------------

        setContentView(R.layout.activity_main);

        // Botones de registro
        Button btnRegisterStudent = findViewById(R.id.btnRegisterStudent);
        Button btnRegisterTeacher = findViewById(R.id.btnRegisterTeacher);
        Button btnLogin = findViewById(R.id.btnLogin);

        // Tarjetas (opcional para efecto visual)
        LinearLayout cardStudent = findViewById(R.id.cardStudent);
        LinearLayout cardTeacher = findViewById(R.id.cardTeacher);

        // Eventos de clic
        btnRegisterStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StudentRegistrationActivity.class);
                startActivity(intent);
            }
        });

        btnRegisterTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TeacherRegistrationActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Efecto de pulsado en tarjetas (opcional)
        cardStudent.setOnClickListener(v -> btnRegisterStudent.performClick());
        cardTeacher.setOnClickListener(v -> btnRegisterTeacher.performClick());
    }
}