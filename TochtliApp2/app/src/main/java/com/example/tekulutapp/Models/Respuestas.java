package com.example.tekulutapp.Models;

import java.io.Serializable;

public class Respuestas implements Serializable {
    String Leccion;
    String R1;
    String R2;
    String R3;
    String R4;
    String pregunta;

    public Respuestas() {    }

    public Respuestas(String leccion, String r1, String r2, String r3, String r4, String pregunta) {
        Leccion = leccion;
        R1 = r1;
        R2 = r2;
        R3 = r3;
        R4 = r4;
        this.pregunta = pregunta;
    }

    public String getLeccion() {
        return Leccion;
    }

    public void setLeccion(String leccion) {
        Leccion = leccion;
    }

    public String getR1() {
        return R1;
    }

    public void setR1(String r1) {
        R1 = r1;
    }

    public String getR2() {
        return R2;
    }

    public void setR2(String r2) {
        R2 = r2;
    }

    public String getR3() {
        return R3;
    }

    public void setR3(String r3) {
        R3 = r3;
    }

    public String getR4() {
        return R4;
    }

    public void setR4(String r4) {
        R4 = r4;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }
}
