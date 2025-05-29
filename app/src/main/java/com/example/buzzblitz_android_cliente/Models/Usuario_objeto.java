package com.example.buzzblitz_android_cliente.Models;

public class Usuario_objeto {
    private String usuario_id;
    private String objeto_nombre;

    public Usuario_objeto() { }

    public Usuario_objeto(String usuario_id, String objeto) {
        this.usuario_id = usuario_id;
        this.objeto_nombre = objeto;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getObjeto() {
        return objeto_nombre;
    }

    public void setObjeto(String objeto) {
        this.objeto_nombre = objeto;
    }
}

