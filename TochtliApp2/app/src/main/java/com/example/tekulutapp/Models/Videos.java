package com.example.tekulutapp.Models;

public class Videos {
    String Nombre;
    String Url;


    public Videos(){}

    public Videos(String Nombre,String Url){
        this.Nombre = Nombre;
        this.Url = Url;
    }
    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
