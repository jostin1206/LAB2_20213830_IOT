package com.example.telequizz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
// Aquì cada botón llevara a MainActivity2
// y le pasarà un extra para saber si se trata de redes, ciber, micro
        Button btnRedes = findViewById(R.id.btnRedes);
        btnRedes.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity2.class);
            intent.putExtra("tema", "Redes");
            startActivity(intent);
        });

        Button btnCiberseguridad = findViewById(R.id.btnCiberseguridad);
        btnCiberseguridad.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity2.class);
            intent.putExtra("tema", "Ciberseguridad");
            startActivity(intent);
        });

        Button btnMicroondas = findViewById(R.id.btnMicroondas);
        btnMicroondas.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity2.class);
            intent.putExtra("tema", "Microondas");
            startActivity(intent);
        });

    }
}