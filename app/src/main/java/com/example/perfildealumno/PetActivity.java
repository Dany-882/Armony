package com.example.perfildealumno;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Random;

public class PetActivity extends AppCompatActivity {

    ImageView ivMascota;
    TextView tvMensaje, tvArniones;
    ProgressBar pbAnimo;
    Button btnTacos, btnBurrito, btnPalomitas;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userId;

    int animoActual = 50;
    int arnionesActuales = 0;

    String[] mensajes = {
            "¡Hola compañero!",
            "Hehehehehe!",
            "¡Pongan Balatro!",
            "Aquí-no djhasjdhajsa",
            "¿Trajiste comida?",
            "¡Vamos a estudiar!"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet);

        ivMascota = findViewById(R.id.ivMascota);
        tvMensaje = findViewById(R.id.tvMensajeMascota);
        tvArniones = findViewById(R.id.tvArniones);
        pbAnimo = findViewById(R.id.pbAnimo);
        btnTacos = findViewById(R.id.btnTacos);
        btnBurrito = findViewById(R.id.btnBurrito);
        btnPalomitas = findViewById(R.id.btnPalomitas);

        userId = getIntent().getStringExtra("userId");

        cargarDatos();
        mostrarMensajeAleatorio();

        btnTacos.setOnClickListener(v -> alimentar(25, 100));
        btnBurrito.setOnClickListener(v -> alimentar(15, 50));
        btnPalomitas.setOnClickListener(v -> alimentar(5, 10));
    }

    private void cargarDatos() {
        DocumentReference ref = db.collection("students").document(userId);
        ref.get().addOnSuccessListener(doc -> {
            if (doc.exists()) {
                animoActual = doc.contains("animo") ? doc.getLong("animo").intValue() : 50;
                arnionesActuales = doc.contains("points") ? doc.getLong("points").intValue() : 0;

                pbAnimo.setProgress(animoActual);
                tvArniones.setText("Arniones: " + arnionesActuales);
            }
        });
    }

    private void mostrarMensajeAleatorio() {
        int idx = new Random().nextInt(mensajes.length);
        tvMensaje.setText(mensajes[idx]);
    }

    private void alimentar(int animoSube, int costo) {

        if (arnionesActuales < costo) {
            Toast.makeText(this, "No tienes suficientes arniones", Toast.LENGTH_SHORT).show();
            return;
        }

        arnionesActuales -= costo;
        animoActual += animoSube;

        if (animoActual > 100) animoActual = 100;

        pbAnimo.setProgress(animoActual);
        tvArniones.setText("Arniones: " + arnionesActuales);

        // Guardar en Firestore
        db.collection("students").document(userId)
                .update("animo", animoActual, "points", arnionesActuales)
                .addOnSuccessListener(a -> Toast.makeText(this, "¡Ñom Ñom!", Toast.LENGTH_SHORT).show());
    }
}