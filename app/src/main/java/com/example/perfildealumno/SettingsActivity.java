package com.example.perfildealumno;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {

    private EditText etNombre, etApellido, etUsername;
    private ImageView ivProfile;
    private Button btnSave, btnCambiarImagen;

    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private StorageReference storageRef;

    private static final int PICK_IMAGE = 100;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Referencias a vistas
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etUsername = findViewById(R.id.etUsername);
        ivProfile = findViewById(R.id.ivProfile);
        btnSave = findViewById(R.id.btnSave);
        btnCambiarImagen = findViewById(R.id.btn_cambiar_imagen);

        // Firebase
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();

        // Tomar el usuario actual (puede ser null, no se valida)
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            cargarDatosUsuario(currentUser.getUid());
        }

        // Botón para cambiar imagen
        btnCambiarImagen.setOnClickListener(v -> abrirGaleria());

        // Botón para guardar cambios
        btnSave.setOnClickListener(v -> {
            if(currentUser != null) {
                guardarCambios(currentUser.getUid());
            }
        });
    }

    private void cargarDatosUsuario(String uid) {
        db.collection("usuarios").document(uid).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if(documentSnapshot.exists()) {
                        etNombre.setText(documentSnapshot.getString("nombre"));
                        etApellido.setText(documentSnapshot.getString("apellido"));
                        etUsername.setText(documentSnapshot.getString("username"));

                        String imageUrl = documentSnapshot.getString("fotoUrl");
                        if(imageUrl != null && !imageUrl.isEmpty()) {
                            Glide.with(this).load(imageUrl).into(ivProfile);
                        }
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error al cargar datos", Toast.LENGTH_SHORT).show()
                );
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            ivProfile.setImageURI(imageUri);
        }
    }

    private void guardarCambios(String uid) {
        String nombre = etNombre.getText().toString().trim();
        String apellido = etApellido.getText().toString().trim();
        String username = etUsername.getText().toString().trim();

        if(nombre.isEmpty() || apellido.isEmpty() || username.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> updates = new HashMap<>();
        updates.put("nombre", nombre);
        updates.put("apellido", apellido);
        updates.put("username", username);

        if(imageUri != null) {
            StorageReference profileRef = storageRef.child("usuarios/" + uid + "/profile.jpg");
            profileRef.putFile(imageUri).addOnSuccessListener(taskSnapshot ->
                    profileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        updates.put("fotoUrl", uri.toString());
                        db.collection("usuarios").document(uid).update(updates)
                                .addOnSuccessListener(aVoid ->
                                        Toast.makeText(this, "Datos actualizados", Toast.LENGTH_SHORT).show()
                                )
                                .addOnFailureListener(e ->
                                        Toast.makeText(this, "Error al actualizar datos", Toast.LENGTH_SHORT).show()
                                );
                    })
            ).addOnFailureListener(e ->
                    Toast.makeText(this, "Error al subir imagen", Toast.LENGTH_SHORT).show()
            );
        } else {
            db.collection("usuarios").document(uid).update(updates)
                    .addOnSuccessListener(aVoid -> Toast.makeText(this, "Datos actualizados", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(this, "Error al actualizar datos", Toast.LENGTH_SHORT).show());
        }
    }
}