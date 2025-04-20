package com.example.buzzblitz_android_cliente.Models;

public class IntercambioRequest {
    private String usuarioId;
    private int cantidadFlores;

    public IntercambioRequest(String usuarioId, int cantidadFlores) {
        this.usuarioId = usuarioId;
        this.cantidadFlores = cantidadFlores;
    }

    // Getters y Setters
    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getCantidadFlores() {
        return cantidadFlores;
    }

    public void setCantidadFlores(int cantidadFlores) {
        this.cantidadFlores = cantidadFlores;
    }
}