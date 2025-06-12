package com.example.buzzblitz_android_cliente.Models;

public class ChatIndividual {
    String nameFrom;
    String nameTo;
    String comentario;

    public ChatIndividual(){}

    public ChatIndividual(String nameFrom, String nameTo, String comentario) {
        this.nameFrom = nameFrom;
        this.nameTo = nameTo;
        this.comentario = comentario;
    }

    public String getNameFrom() {
        return nameFrom;
    }

    public void setNameFrom(String nameFrom) {
        this.nameFrom = nameFrom;
    }

    public String getNameTo() {
        return nameTo;
    }

    public void setNameTo(String nameTo) {
        this.nameTo = nameTo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
