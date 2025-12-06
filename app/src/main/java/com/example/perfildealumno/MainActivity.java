package com.example.perfildealumno;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
