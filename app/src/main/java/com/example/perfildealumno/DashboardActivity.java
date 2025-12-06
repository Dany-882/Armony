package com.example.perfildealumno;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class DashboardActivity extends AppCompatActivity {
    TextView tvUserName, tvPoints, tvClasses, tvGames, tvStreak, tvTasks;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userId; // Lo recibes desde Login

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tvUserName = findViewById(R.id.tvUserName);
        tvPoints = findViewById(R.id.tvPoints);
        tvClasses = findViewById(R.id.tvClasses);
        tvGames = findViewById(R.id.tvGames);
        tvStreak = findViewById(R.id.tvStreak);
        tvTasks = findViewById(R.id.tvTasks);

        userId = getIntent().getStringExtra("userId");

        loadUserData();
    }

    private void loadUserData() {
        DocumentReference ref = db.collection("students").document(userId);

        ref.get().addOnSuccessListener(doc -> {
            if (doc.exists()) {

                String name = doc.getString("name");
                long points = doc.contains("points") ? doc.getLong("points") : 0;
                long classes = doc.contains("classes") ? doc.getLong("classes") : 0;
                long games = doc.contains("games") ? doc.getLong("games") : 0;
                long streak = doc.contains("streak") ? doc.getLong("streak") : 0;

                tvUserName.setText(name);
                tvPoints.setText("Puntos: " + points);
                tvClasses.setText("Clases completadas: " + classes);
                tvGames.setText("Juegos completados: " + games);
                tvStreak.setText("Días de racha: " + streak);

            }
        });
    }
}