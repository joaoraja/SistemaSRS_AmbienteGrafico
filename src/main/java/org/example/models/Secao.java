package org.example.models;

import java.util.HashMap;

public class Secao {
    private final Curso cursoRepresentado;
    private final int numeroSecao;
    private char diaSemana;
    private String horario;
    private String sala;
    private int capacidadeAssentos;
    private Professor instrutor;
    private final HashMap<String, Aluno> alunosMatriculados;
    private final HashMap<Aluno, RegistroHistorico> notasAtribuidas;
    private final Semestre semestre;

    public Secao(Curso curso, int numero, char dia, String horario, String sala, int capacidade) {
        this.cursoRepresentado = curso;
        this.numeroSecao = numero;
        this.diaSemana = dia;
        this.horario = horario;
        this.sala = sala;
        this.capacidadeAssentos = capacidade;
        this.alunosMatriculados = new HashMap<>();
        this.notasAtribuidas = new HashMap<>();
        this.semestre = new Semestre("2025.1");
    }

    public String getIdentificadorCompleto() {
        return cursoRepresentado.getCodigoCurso() + "-" + numeroSecao;
    }

    public Curso getCursoRepresentado() {
        return cursoRepresentado;
    }

    public int getNumeroSecao() {
        return numeroSecao;
    }

    public char getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(char diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public int getCapacidadeAssentos() {
        return capacidadeAssentos;
    }

    public void setCapacidadeAssentos(int capacidadeAssentos) {
        this.capacidadeAssentos = capacidadeAssentos;
    }

    public Professor getInstrutor() {
        return instrutor;
    }

    public void setInstrutor(Professor instrutor) {
        this.instrutor = instrutor;
    }

    public HashMap<String, Aluno> getAlunosMatriculados() {
        return alunosMatriculados;
    }

    public HashMap<Aluno, RegistroHistorico> getNotasAtribuidas() {
        return notasAtribuidas;
    }

    public void registrarNota(Aluno aluno, String nota) {
        RegistroHistorico rh = new RegistroHistorico(aluno, this, nota);
        notasAtribuidas.put(aluno, rh);
        aluno.getHistorico().adicionarRegistro(rh);
    }

    public String consultarNota(Aluno aluno) {
        RegistroHistorico rh = notasAtribuidas.get(aluno);
        return rh != null ? rh.getNota() : null;
    }

    public boolean matricularAluno(Aluno aluno) {
        if (aluno == null) return false;
        if (alunosMatriculados.size() >= capacidadeAssentos) return false;
        alunosMatriculados.put(aluno.getCpf(), aluno);
        aluno.adicionarSecao(this);
        return true;
    }

    public void lancarNota(Aluno aluno, String nota) {
        RegistroHistorico registro = new RegistroHistorico(aluno, this, nota);
        aluno.getHistorico().adicionarRegistro(registro);
        notasAtribuidas.put(aluno, registro);
    }

    public Semestre getOfertadaEm() {
        return semestre;
    }
}