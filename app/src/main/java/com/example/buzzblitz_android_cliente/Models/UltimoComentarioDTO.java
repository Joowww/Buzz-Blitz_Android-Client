package com.example.buzzblitz_android_cliente.Models;

public class UltimoComentarioDTO {
    private String tema;
    private String ultimoComentario;
    private String descripcion; // Opcional, si tu backend lo devuelve
    private int numComentarios; // Opcional, si tu backend lo devuelve

    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }

    public String getUltimoComentario() { return ultimoComentario; }
    public void setUltimoComentario(String ultimoComentario) { this.ultimoComentario = ultimoComentario; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getNumComentarios() { return numComentarios; }
    public void setNumComentarios(int numComentarios) { this.numComentarios = numComentarios; }
}