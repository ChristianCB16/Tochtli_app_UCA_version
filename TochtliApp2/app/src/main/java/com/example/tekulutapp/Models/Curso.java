package com.example.tekulutapp.Models;

import java.io.Serializable;

public class Curso  implements Serializable {
    String id;
    String usuario;
    String descripcion;
    String nombre;
    int progreso;

    public Curso(){}

    public Curso(String usuario, String descripcion, String nombre,int progreso){
        this.usuario = usuario;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.progreso = progreso;
    }

    public Curso(String id,String usuario, String descripcion, String nombre,int progreso){
        this.id = id;
        this.usuario = usuario;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.progreso = progreso;
    }
    //llave de tabla
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCorreo() {
        return usuario;
    }

    public void setCorreo(String usuario) {
        this.usuario = usuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {return nombre;}

    public void setNombre_curso(String nombre) {
        this.nombre = nombre;
    }

    public int getProgreso() {
        return progreso;
    }

    public void setProgreso(int progreso) {
        this.progreso = progreso;
    }
}
