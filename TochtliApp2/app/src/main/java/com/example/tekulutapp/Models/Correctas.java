package com.example.tekulutapp.Models;

import java.io.Serializable;

public class Correctas implements Serializable {

    String Leccion;
    String Pregunta;
    String Respuesta;


    public Correctas(){}

    public Correctas(String leccion, String pregunta, String respuesta) {
        Leccion = leccion;
        Pregunta = pregunta;
        Respuesta = respuesta;
    }

    public String getLeccion() {
        return Leccion;
    }

    public void setLeccion(String leccion) {
        Leccion = leccion;
    }

    public String getPregunta() {
        return Pregunta;
    }

    public void setPregunta(String pregunta) {
        Pregunta = pregunta;
    }

    public String getRespuesta() {
        return Respuesta;
    }

    public void setRespuesta(String respuesta) {
        Respuesta = respuesta;
    }
}
