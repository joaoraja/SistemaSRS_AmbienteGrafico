package org.example;
import org.example.models.*;

import java.util.ArrayList;

public class Main {
    // "Tabelas" em memória
    public static Usuario usuarioLogado;
    public static ArrayList<Aluno> corpoDiscente = new ArrayList<>();
    public static ArrayList<Professor> docentes = new ArrayList<>();
    public static ArrayList<Curso> catalogoCursos = new ArrayList<>();
    public static GradeHorarios gradeHorarios = new GradeHorarios();// contém as seções (disciplinas)

    public static void carregarDadosIniciais() {
        if (!catalogoCursos.isEmpty()) return; // evita duplicar

        // Cursos
        Curso cursoTI = new Curso("TI01", "Tecnologia da Informação", 60);
        Curso cursoADM = new Curso("ADM01", "Administração", 50);
        Curso cursoENG = new Curso("ENG01", "Engenharia", 80);
        catalogoCursos.add(cursoTI);
        catalogoCursos.add(cursoADM);
        catalogoCursos.add(cursoENG);

        // Professores
        Professor prof1 = new Professor("Vitor Amadeu", "111.111.111-11", "Doutor", "Tecnologia da Informação");
        Professor prof2 = new Professor("Cristiano Ronaldo", "222.222.222-22", "Mestre", "Administração");
        Professor prof3 = new Professor("Lionel Messi", "333.333.333-33", "Mestre", "Engenharia");
        docentes.add(prof1);
        docentes.add(prof2);
        docentes.add(prof3);

        // Seções
        Secao sec1 = cursoTI.agendarSecao('T', "18:00 - 20:00", "Sala 101", 40);
        Secao sec2 = cursoADM.agendarSecao('Q', "14:00 - 16:00", "Sala 202", 30);
        Secao sec3 = cursoADM.agendarSecao('S', "13:00 - 15:00", "Sala 205", 20);
        gradeHorarios.adicionarSecao(sec1);
        gradeHorarios.adicionarSecao(sec2);
        gradeHorarios.adicionarSecao(sec3);

        // Alunos
        Aluno aluno1 = new Aluno("Neymar Jr", "123.456.789-00", "Tecnologia da Informação", "Bacharelado");
        Aluno aluno2 = new Aluno("John Kennedy", "987.654.321-00", "Administração", "Tecnólogo");
        Aluno aluno3 = new Aluno("Steve Jobs", "987.654.321-00", "Engenharia", "Bacharelado");
        corpoDiscente.add(aluno1);
        corpoDiscente.add(aluno2);
        corpoDiscente.add(aluno3);

        // Matrículas
        sec1.matricularAluno(aluno1);
        sec2.matricularAluno(aluno2);
        sec3.matricularAluno(aluno3);

        // Notas lançadas
        sec1.lancarNota(aluno1, "A");
        sec2.lancarNota(aluno2, "B+");
        sec3.lancarNota(aluno3, "R");

        // Alocar professores
        prof1.aceitarEnsinar(sec1);
        prof2.aceitarEnsinar(sec2);
        prof3.aceitarEnsinar(sec3);

    }
    // Adiciona dados iniciais "hardcoded" (sem banco real)
}