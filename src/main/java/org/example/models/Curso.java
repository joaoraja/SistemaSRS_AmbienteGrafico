package org.example.models;

import java.util.ArrayList;

public class Curso {
    private final String codigoCurso;
    private String nomeCurso;
    private double creditos;
    private final ArrayList<Curso> preRequisitos;
    private final ArrayList<Secao> secoesOfertadas;

    public Curso(String codigoCurso, String nomeCurso, double creditos) {
        this.codigoCurso = codigoCurso;
        this.nomeCurso = nomeCurso;
        this.creditos = creditos;
        this.preRequisitos = new ArrayList<>();
        this.secoesOfertadas = new ArrayList<>();
    }

    public String getCodigoCurso() {
        return codigoCurso;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public double getCreditos() {
        return creditos;
    }

    public void setCreditos(double creditos) {
        this.creditos = creditos;
    }

    public ArrayList<Curso> getPreRequisitos() {
        return preRequisitos;
    }

    public void adicionarPreRequisito(Curso c) {
        this.preRequisitos.add(c);
    }

    public Secao agendarSecao(char dia, String horario, String sala, int capacidade) {
        int numero = secoesOfertadas.size() + 1;
        Secao nova = new Secao(this, numero, dia, horario, sala, capacidade);
        secoesOfertadas.add(nova);
        return nova;
    }
}