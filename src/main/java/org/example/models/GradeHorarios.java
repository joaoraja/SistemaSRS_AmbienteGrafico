package org.example.models;

import java.util.HashMap;

//Simula o armazenamento das seções de disciplinas:
public class GradeHorarios {
    private final HashMap<String, Secao> secoesOfertadas;

    public GradeHorarios() {
        this.secoesOfertadas = new HashMap<>();
    }

    public void adicionarSecao(Secao secao) {
        secoesOfertadas.put(secao.getIdentificadorCompleto(), secao);
    }

    public Secao localizarSecao(String identificador) {
        return secoesOfertadas.get(identificador);
    }

    public boolean estaVazia() {
        return secoesOfertadas.isEmpty();
    }

    public HashMap<String, Secao> getSecoesOfertadas() {
        return secoesOfertadas;
    }
}