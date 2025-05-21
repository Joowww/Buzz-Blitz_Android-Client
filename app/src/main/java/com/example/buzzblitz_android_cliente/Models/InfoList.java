package com.example.buzzblitz_android_cliente.Models;
import java.util.List;
public class InfoList {
    private List<Info> ranking;

    public InfoList() {}

    public InfoList(List<Info> ranking) {
        this.ranking = ranking;
    }

    public List<Info> getRanking() {
        return ranking;
    }

    public void setRanking(List<Info> ranking) {
        this.ranking = ranking;
    }
}
