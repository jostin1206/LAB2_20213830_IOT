package com.example.telequizz;

import java.util.List;

public class Pregunta {
    private String enunciado;
    private List<String> opciones;
    private String respuestaCorrecta;

    public Pregunta(String enunciado, List<String> opciones, String respuestaCorrecta) {
        this.enunciado = enunciado;
        this.opciones = opciones;
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public List<String> getOpciones() {
        return opciones;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }
}
