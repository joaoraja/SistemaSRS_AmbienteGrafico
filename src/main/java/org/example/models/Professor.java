package org.example.models;

public class Professor {
    private String nome;
    private String cpf;
    private String titulo;
    private String departamento;

    public Professor(String nome, String cpf, String titulo, String departamento) {
        this.nome = nome;
        this.cpf = cpf;
        this.titulo = titulo;
        this.departamento = departamento;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public void aceitarEnsinar(Secao secao) {
        secao.setInstrutor(this);
    }
}