package org.example.models;

public class RegistroHistorico {
    private Aluno aluno;
    private Secao secao;
    private String nota;

    public RegistroHistorico(Aluno aluno, Secao secao, String nota) {
        this.aluno = aluno;
        this.secao = secao;
        this.nota = nota;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public Secao getSecao() {
        return secao;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public static boolean validarNota(String nota) {
        return nota.matches("A|A-|B\\+|B|B-|C\\+|C|C-|D|F");
    }
}