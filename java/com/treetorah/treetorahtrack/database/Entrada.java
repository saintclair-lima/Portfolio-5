package com.treetorah.treetorahtrack.database;

public class Entrada {
    //private int id;
    private int ano;
    private String uf;
    private int cortadas;
    private float volume;
    private int repostas;
    private double valorPagar;

    public Entrada(){}

    public Entrada(String[] valores){
        //this.setId(0);
        this.setAno(Integer.parseInt(valores[0]));
        this.setUf(valores[1]);
        this.setCortadas(Integer.parseInt(valores[2]));
        this.setVolume(Float.parseFloat(valores[3]));
        this.setRepostas(Integer.parseInt(valores[4]));
        this.setValorPagar(Float.parseFloat(valores[5]));
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public int getCortadas() {
        return cortadas;
    }

    public void setCortadas(int cortadas) {
        this.cortadas = cortadas;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public int getRepostas() {
        return repostas;
    }

    public void setRepostas(int repostas) {
        this.repostas = repostas;
    }

    public double getValorPagar() {
        return valorPagar;
    }

    public void setValorPagar(double valorPagar) {
        this.valorPagar = valorPagar;
    }

/*    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }*/
}
