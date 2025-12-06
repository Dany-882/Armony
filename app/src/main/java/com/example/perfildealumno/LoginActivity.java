package com.example.perfildealumno;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth auth = FirebaseAuth.getInstance();

    EditText etEmailLogin, etPasswordLogin;
    Button btnLogin;
    ImageButton btnBack;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmailLogin = findViewById(R.id.etEmailLogin);
        etPasswordLogin = findViewById(R.id.etPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());

        btnLogin.setOnClickListener(v -> {
            String email = etEmailLogin.getText().toString().trim();
            String password = etPasswordLogin.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Ingresa tu correo y contraseña", Toast.LENGTH_SHORT).show();
                return;
            }

            loginStudent(email, password);
        });
    }

    private void loginStudent(String email, String password) {

        db.collection("students")
                .document(email) // usas el email como ID
                .get()
                .addOnSuccessListener(document -> {

                    if (!document.exists()) {
                        Toast.makeText(this, "El correo no está registrado", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String correctPassword = document.getString("password");

                    if (!password.equals(correctPassword)) {
                        Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Éxito
                    Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(LoginActivity.this, DashboardActivity.class);

                    // 🔥 SOLO ESTO SE AÑADE 🔥
                    i.putExtra("userId", email);

                    // Este es el tuyo original, lo dejo
                    i.putExtra("nombre_usuario", "Ana");

                    startActivity(i);

                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show()
                );
    }
}
