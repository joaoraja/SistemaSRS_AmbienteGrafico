package org.example.models;

import java.util.ArrayList;

public class Historico {
    private Aluno aluno;
    private ArrayList<RegistroHistorico> registros;

    public Historico(Aluno aluno) {
        this.aluno = aluno;
        this.registros = new ArrayList<>();
    }

    public void adicionarRegistro(RegistroHistorico registro) {
        registros.add(registro);
    }

    public ArrayList<RegistroHistorico> getRegistros() {
        return registros;
    }
}