package com.example.buzzblitz_android_cliente.Models;

import android.content.Context;
import com.example.buzzblitz_android_cliente.R;

public class Objeto {
    private String id;
    private String nombre;
    private Integer precio;
    private int tipo;
    private String descripcion;
    private String imagenResId;

    public Objeto() {}

    public Objeto(String id, String nombre, int precio , int tipo, String descripcion ,String imagenResId) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.descripcion = descripcion;
        this.imagenResId = imagenResId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getImagenResId() {
        return imagenResId;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setImagenResId(String imagenResId) {
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
    public void setNombreImagen(String nombreImagen) { this.imagenResId = nombreImagen; }

    public int getImagenResId(Context context) {
        int resId = context.getResources()
                .getIdentifier(imagenResId, "drawable", context.getPackageName());
        return resId != 0 ? resId : R.drawable.bee;
    }
}