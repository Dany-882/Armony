package com.example.perfildealumno;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

// --------------------------- CLASS ----------------------------------
public class StudentRegistrationActivity extends AppCompatActivity {

    EditText etName, etLastName, etUsername, etEmail, etPhone, etAge, etPassword;
    Button btnCreateAccount, btnGenderM, btnGenderF, btnGenderOther;
    LinearLayout layoutVisual, layoutAuditory, layoutKinesthetic;

    String gender = "";
    String learningStyle = "";

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        // Inputs
        etName = findViewById(R.id.etName);
        etLastName = findViewById(R.id.etLastName);
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etAge = findViewById(R.id.etAge);
        etPassword = findViewById(R.id.etPassword);

        // Botones género
        btnGenderM = findViewById(R.id.btnGenderM);
        btnGenderF = findViewById(R.id.btnGenderF);
        btnGenderOther = findViewById(R.id.btnGenderOther);

        // Estilos
        layoutVisual = findViewById(R.id.layoutVisual);
        layoutAuditory = findViewById(R.id.layoutAuditory);
        layoutKinesthetic = findViewById(R.id.layoutKinesthetic);

        // Botón crear cuenta
        btnCreateAccount = findViewById(R.id.btnCreateAccount);


        // ----------------------------------------------
        //        SELECCIÓN DE GÉNERO
        // ----------------------------------------------
        btnGenderM.setOnClickListener(v -> gender = "M");
        btnGenderF.setOnClickListener(v -> gender = "F");
        btnGenderOther.setOnClickListener(v -> gender = "O");


        // ----------------------------------------------
        //        SELECCIÓN DE TIPO DE APRENDIZAJE
        // ----------------------------------------------
        layoutVisual.setOnClickListener(v -> learningStyle = "Visual");
        layoutAuditory.setOnClickListener(v -> learningStyle = "Auditivo");
        layoutKinesthetic.setOnClickListener(v -> learningStyle = "Kinestésico");


        // ----------------------------------------------
        //     BOTÓN CREAR CUENTA (GUARDAR EN FIRESTORE)
        // ----------------------------------------------
        btnCreateAccount.setOnClickListener(v -> {

            String name = etName.getText().toString().trim();
            String lastName = etLastName.getText().toString().trim();
            String username = etUsername.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String age = etAge.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // VALIDACIÓN
            if (name.isEmpty() || lastName.isEmpty() || username.isEmpty() ||
                    email.isEmpty() || phone.isEmpty() || age.isEmpty() ||
                    password.isEmpty() || gender.isEmpty() || learningStyle.isEmpty()) {

                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validar usuario repetido con el EMAIL
            db.collection("students")
                    .document(email)
                    .get()
                    .addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {

                                Toast.makeText(this,
                                        "Ya existe una cuenta con este correo",
                                        Toast.LENGTH_LONG).show();

                            } else {
                                guardarUsuario(name, lastName, username,
                                        email, phone, age, password,
                                        gender, learningStyle);
                            }
                        } else {
                            Toast.makeText(this,
                                    "Error al validar correo",
                                    Toast.LENGTH_SHORT).show();
                        }

                    });
        });
    }


    private void guardarUsuario(String name, String lastName, String username,
                                String email, String phone, String age, String password,
                                String gender, String learningStyle) {

        Map<String, Object> student = new HashMap<>();
        student.put("nombre", name);
        student.put("apellido", lastName);
        student.put("username", username);
        student.put("email", email);
        student.put("celular", phone);
        student.put("edad", age);
        student.put("password", password);
        student.put("genero", gender);
        student.put("aprendizaje", learningStyle);

        db.collection("students")
                .document(email)
                .set(student)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        Toast.makeText(this,
                                "Cuenta creada correctamente",
                                Toast.LENGTH_SHORT).show();

                        // IR AL DASHBOARD
                        Intent intent = new Intent(this, DashboardActivity.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(this,
                                "Error al crear la cuenta",
                                Toast.LENGTH_SHORT).show();
                    }

                });
    }
}