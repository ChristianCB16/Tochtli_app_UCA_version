package com.example.tekulutapp.Models;

import java.io.Serializable;

public class Preguntas implements Serializable {
    String correo;
    int nota;
    String Leccion;
    String pregunta;

    public Preguntas(){}

    public Preguntas(String correo, String leccion, int nota, String pregunta) {
        this.correo = correo;
        this.nota = nota;
        Leccion = leccion;
        this.pregunta = pregunta;
    }



    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getLeccion() {
        return Leccion;
    }

    public void setLeccion(String leccion) {
        Leccion = leccion;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }
}
