package com.example.telequizz;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    private List<Pregunta> preguntas = new ArrayList<>();
    private int indiceActual = 0;
    private int puntaje = 0;
    private String tema;

    // Referencias a la interfaz
    TextView textTema, textPregunta, textResultado,textPuntaje;
    Button opcion1, opcion2, opcion3, btnSiguiente, btnAnterior;
    private List<String> respuestasUsuario = new ArrayList<>();
    private List<Boolean> respuestasCorrectas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main2), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtener el tema
        tema = getIntent().getStringExtra("tema");

        // Referencias UI
        textTema = findViewById(R.id.textTema);
        textPregunta = findViewById(R.id.textPregunta);
        textPuntaje = findViewById(R.id.textPuntaje);
        textResultado = findViewById(R.id.textResultado);
        opcion1 = findViewById(R.id.opcion1);
        opcion2 = findViewById(R.id.opcion2);
        opcion3 = findViewById(R.id.opcion3);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        btnAnterior = findViewById(R.id.btnAnterior);
        textTema.setText("Tema: " + tema);
        btnAnterior.setOnClickListener(v -> {
            if (indiceActual > 0) {
                indiceActual--;
                mostrarPregunta();
            }
        });

        // Cargamos las preguntas
        preguntas = obtenerPreguntas (tema);
        // Inicializamos listas con valores por defecto
        for (int i = 0; i < preguntas.size(); i++) {
            respuestasUsuario.add("");        // aún no ha respondido nada
            respuestasCorrectas.add(null);
        }

        Collections.shuffle(preguntas); //Forma aleatoria

        mostrarPregunta();

        btnSiguiente.setOnClickListener(v -> {
            indiceActual++;
            if (indiceActual < preguntas.size()) {
                mostrarPregunta();
            } else {
                Toast.makeText(this, "¡Juego terminado! Puntaje: " + puntaje + "/5", Toast.LENGTH_LONG).show();
                btnSiguiente.setEnabled(false);
                opcion1.setEnabled(false);
                opcion2.setEnabled(false);
                opcion3.setEnabled(false);
            }
        });

    }

    private void mostrarPregunta() {
        Pregunta pregunta = preguntas.get(indiceActual);
        textResultado.setText("");
        textPregunta.setText((indiceActual + 1) + ". " + pregunta.getEnunciado());
        textPuntaje.setText("Puntaje: " + puntaje);

        // Restaurar color y habilitar botones
        opcion1.setEnabled(true);
        opcion2.setEnabled(true);
        opcion3.setEnabled(true);
// reseteamos los colores
        opcion1.setBackgroundColor(getResources().getColor(R.color.defaultButton));
        opcion2.setBackgroundColor(getResources().getColor(R.color.defaultButton));
        opcion3.setBackgroundColor(getResources().getColor(R.color.defaultButton));

        List<String> opciones = new ArrayList<>(pregunta.getOpciones());
        Collections.shuffle(opciones);

        opcion1.setText(opciones.get(0));
        opcion2.setText(opciones.get(1));
        opcion3.setText(opciones.get(2));

        String respuestaGuardada = respuestasUsuario.get(indiceActual);
        Boolean fueCorrecta = respuestasCorrectas.get(indiceActual);

        if (!respuestaGuardada.isEmpty()) {
            // Ya fue respondida entonces mostrar colores y desactivar
            Button[] botones = {opcion1, opcion2, opcion3};
            for (Button btn : botones) {
                if (btn.getText().toString().equals(respuestaGuardada)) {
                    int color = fueCorrecta != null && fueCorrecta
                            ? getResources().getColor(R.color.verdeCorrecto)
                            : getResources().getColor(R.color.rojoIncorrecto);
                    btn.setBackgroundColor(color);
                }
                btn.setEnabled(false);
            }

            textResultado.setText(fueCorrecta ? "✅ ¡Correcto!" : "❌ Incorrecto");
            btnSiguiente.setEnabled(true);

        } else {
            // Aún no fue respondida se configura listeners
            View.OnClickListener listener = v -> {
                Button b = (Button) v;
                String respuesta = b.getText().toString();
                respuestasUsuario.set(indiceActual, respuesta);

                boolean correcto = respuesta.equals(pregunta.getRespuestaCorrecta());
                respuestasCorrectas.set(indiceActual, correcto);

                if (correcto) {
                    textResultado.setText("✅ ¡Correcto!");
                    b.setBackgroundColor(getResources().getColor(R.color.verdeCorrecto));
                    puntaje += 2;
                } else {
                    textResultado.setText("❌ Incorrecto");
                    b.setBackgroundColor(getResources().getColor(R.color.rojoIncorrecto));
                    puntaje -= 2;
                }

                textPuntaje.setText("Puntaje: " + puntaje);
                opcion1.setEnabled(false);
                opcion2.setEnabled(false);
                opcion3.setEnabled(false);
                btnSiguiente.setEnabled(true);
            };

            opcion1.setOnClickListener(listener);
            opcion2.setOnClickListener(listener);
            opcion3.setOnClickListener(listener);
            btnSiguiente.setEnabled(false);
        }
    }



    private List<Pregunta> obtenerPreguntas(String tema) {
        List<Pregunta> lista = new ArrayList<>();

        if (tema.equals("Redes")) {
            lista.add(new Pregunta("¿Qué protocolo se utiliza para cargar páginas web?", Arrays.asList("HTTP", "FTP", "SSH"), "HTTP"));
            lista.add(new Pregunta("¿Cuál es una IP privada?", Arrays.asList("192.168.1.1", "8.8.8.8", "23.45.67.89"), "192.168.1.1"));
            lista.add(new Pregunta("¿Qué dispositivo conecta redes?", Arrays.asList("Router", "Switch", "Bridge"), "Router"));
            lista.add(new Pregunta("¿Qué significa DNS?", Arrays.asList("Sistema de nombres de dominio", "Red privada", "Dispositivo de red"), "Sistema de nombres de dominio"));
            lista.add(new Pregunta("¿Qué tipo de red cubre una oficina?", Arrays.asList("LAN", "WAN", "PAN"), "LAN"));
        } else if (tema.equals("Ciberseguridad")) {
            lista.add(new Pregunta("¿Qué es un Ransomware?", Arrays.asList("Malware que pide rescate", "Antivirus", "Firewall"), "Malware que pide rescate"));
            lista.add(new Pregunta("¿Qué es phishing?", Arrays.asList("Robo de datos por engaño", "Backup de info", "Protección antivirus"), "Robo de datos por engaño"));
            lista.add(new Pregunta("¿Qué protocolo cifra la web?", Arrays.asList("HTTPS", "HTTP", "FTP"), "HTTPS"));
            lista.add(new Pregunta("¿Qué algoritmo es asimétrico?", Arrays.asList("RSA", "AES", "SHA"), "RSA"));
            lista.add(new Pregunta("¿Para qué sirve un firewall?", Arrays.asList("Filtrar conexiones", "Enviar emails", "Medir velocidad"), "Filtrar conexiones"));
        } else if (tema.equals("Microondas")) {
            lista.add(new Pregunta("¿Qué frecuencias usa Wi-Fi?", Arrays.asList("2.4 GHz y 5 GHz", "900 MHz", "20 GHz"), "2.4 GHz y 5 GHz"));
            lista.add(new Pregunta("¿Problema común en microondas?", Arrays.asList("Interferencia", "Latencia", "Luz solar"), "Interferencia"));
            lista.add(new Pregunta("¿Qué es zona de Fresnel?", Arrays.asList("Área de propagación", "Conector", "Repetidor"), "Área de propagación"));
            lista.add(new Pregunta("¿Ventaja de microondas?", Arrays.asList("Alta velocidad", "Fácil instalación", "Uso interior"), "Alta velocidad"));
            lista.add(new Pregunta("¿Qué dispositivo enfoca señales?", Arrays.asList("Antena parabólica", "Router", "Switch"), "Antena parabólica"));
        }

        return lista.subList(0, 5); // solo 5 preguntas
    }

}