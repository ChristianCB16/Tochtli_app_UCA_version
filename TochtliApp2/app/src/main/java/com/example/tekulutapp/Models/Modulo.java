package com.example.tekulutapp.Models;

import java.io.Serializable;

public class Modulo  implements Serializable {
    String id;
    String curso;
    String descripcion;
    String nombre;
    int Progreso;
    boolean estado;
    public Modulo(){}

    public Modulo(String curso, String descripcion, String nombre,int progreso, boolean estado){
        this.curso = curso;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.Progreso = progreso;
        this.estado = estado;
    }
    public Modulo(String id, String curso, String descripcion, String nombre,int progreso, boolean estado){
        this.id = id;
        this.curso = curso;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.Progreso = progreso;
        this.estado = estado;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getProgreso() {
        return Progreso;
    }

    public void setProgreso(int progreso) {
        this.Progreso = progreso;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {this.estado = estado;}


}
