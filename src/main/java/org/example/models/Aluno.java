package org.example.models;

import java.util.ArrayList;

public class Aluno {
    private String nome;
    private final String cpf;
    private String curso;
    private String grau;
    private final ArrayList<Secao> secoesMatriculadas;
    private final Historico historico;

    public Aluno(String nome, String cpf, String curso, String grau) {
        this.nome = nome;
        this.cpf = cpf;
        this.curso = curso;
        this.grau = grau;
        this.secoesMatriculadas = new ArrayList<>();
        this.historico = new Historico(this);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getGrau() {
        return grau;
    }

    public void setGrau(String grau) {
        this.grau = grau;
    }

    public ArrayList<Secao> getSecoesMatriculadas() {
        return secoesMatriculadas;
    }

    public void adicionarSecao(Secao s) {
        this.secoesMatriculadas.add(s);
    }

    public Historico getHistorico() {
        return historico;
    }
}