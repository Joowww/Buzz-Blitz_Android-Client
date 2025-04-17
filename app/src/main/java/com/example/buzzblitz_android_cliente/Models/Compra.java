package com.example.buzzblitz_android_cliente.Models;

public class Compra {
    private String usuarioId;
    private Objeto objeto;

    public Compra() { }

    public Compra(String usuarioId, Objeto objeto) {
        this.usuarioId = usuarioId;
        this.objeto = objeto;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Objeto getObjeto() {
        return objeto;
    }

    public void setObjeto(Objeto objeto) {
        this.objeto = objeto;
    }
}

