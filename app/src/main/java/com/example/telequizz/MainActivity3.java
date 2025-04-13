package com.example.telequizz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.estadisticasLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Recibir datos del Intent
        Intent intent = getIntent();
        int puntajeFinal = intent.getIntExtra("puntajeFinal", 0);
        String temaFinal = intent.getStringExtra("temaFinal");

        // Referencias
        TextView textTemaFinal = findViewById(R.id.textTemaFinal);
        TextView textPuntajeFinal = findViewById(R.id.textPuntajeFinal);

        // Mostrar datos
        textTemaFinal.setText(temaFinal);
        textPuntajeFinal.setText(String.valueOf(puntajeFinal));

        // Cambiar color de fondo según puntaje
        if (puntajeFinal >= 0) {
            textPuntajeFinal.setBackgroundColor(getResources().getColor(R.color.verdeCorrecto));
        } else {
            textPuntajeFinal.setBackgroundColor(getResources().getColor(R.color.rojoIncorrecto));
        }
    }


}