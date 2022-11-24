package com.example.tekulutapp.Models;

import java.io.Serializable;

public class Leccion implements Serializable {
    String id;
    String curso;
    String descripcion;
    String modulo;
    String nombre;
    String url;
    boolean estado;

    public Leccion(){}
    public Leccion(String curso, String descripcion, String modulo, String nombre, String url, boolean estado) {
        this.curso = curso;
        this.descripcion = descripcion;
        this.modulo = modulo;
        this.nombre = nombre;
        this.url = url;
        this.estado = estado;
    }
    public Leccion(String id, String curso, String descripcion, String modulo, String url, String nombre, boolean estado) {
        this.id = id;
        this.curso = curso;
        this.descripcion = descripcion;
        this.modulo = modulo;
        this.url = url;
        this.nombre = nombre;
        this.estado = estado;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getUrl(){return url;}

    public void setUrl(String url){this.url = url;}

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
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
