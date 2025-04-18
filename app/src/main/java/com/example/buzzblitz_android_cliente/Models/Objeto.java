package com.example.buzzblitz_android_cliente.Models;

public class Objeto {
    private String id;
    private String nombre;
    private Integer precio;

    private int imagenResId;
    private int tipo; // "arma" o "skin"

    public Objeto() {}

    public Objeto(String id, String nombre, int precio ,int tipo, int imagenResId) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.imagenResId = imagenResId;
    }
    public int getPrecio() {
        return this.precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {

        this.id = id;
    }
    public int getImagenResId() {
        return imagenResId;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {

        this.nombre = nombre;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {

        this.tipo = tipo;
    }
}


