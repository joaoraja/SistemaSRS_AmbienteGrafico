package org.example.controllers;
import org.example.*;
import javax.swing.*;
import org.example.models.Professor;
import org.example.models.Secao;
import org.example.utils.Validador;

public class ProfessorController {

    public static void cadastrarProfessor(JFrame parent) {
        String nome = JOptionPane.showInputDialog(parent, "Nome do professor:");
        if (nome == null || nome.isEmpty()) return;

        String cpf = JOptionPane.showInputDialog(parent, "CPF do professor:");
        if (cpf == null || cpf.isEmpty()) return;

// AQUI:
        if (!Validador.validarCPF(cpf)) {
            JOptionPane.showMessageDialog(parent, "CPF inválido. Use o formato 000.000.000-00", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean existe = Main.docentes.stream().anyMatch(p -> p.getCpf().equals(cpf));

        if (existe) {
            JOptionPane.showMessageDialog(parent, "CPF já cadastrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] titulos = {"Especialista", "Mestre", "Doutor", "Pós-doutor"};
        String titulo = (String) JOptionPane.showInputDialog(parent, "Título do professor:", "Título", JOptionPane.PLAIN_MESSAGE, null, titulos, titulos[0]);
        if (titulo == null) return;

        String[] departamentos = {"TI", "Engenharia", "Matemática", "Física", "Química", "Letras"};
        String departamento = (String) JOptionPane.showInputDialog(parent, "Departamento:", "Departamento", JOptionPane.PLAIN_MESSAGE, null, departamentos, departamentos[0]);
        if (departamento == null) return;

        Professor novo = new Professor(nome, cpf, titulo, departamento);
        Main.docentes.add(novo);

        JOptionPane.showMessageDialog(parent, "Professor cadastrado com sucesso.");
    }

    public static void editarProfessor(JFrame parent) {
        if (Main.docentes.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Nenhum professor cadastrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] professores = Main.docentes.stream()
                .map(p -> p.getCpf() + " - " + p.getNome())
                .toArray(String[]::new);

        String escolha = (String) JOptionPane.showInputDialog(parent, "Selecione o professor:", "Editar Professor", JOptionPane.PLAIN_MESSAGE, null, professores, professores[0]);
        if (escolha == null) return;

        String cpf = escolha.split(" - ")[0];
        Professor professor = Main.docentes.stream().filter(p -> p.getCpf().equals(cpf)).findFirst().orElse(null);
        if (professor == null) return;

        String novoNome = JOptionPane.showInputDialog(parent, "Novo nome:", professor.getNome());
        if (novoNome == null || novoNome.isEmpty()) return;

        String[] titulos = {"Especialista", "Mestre", "Doutor", "Pós-doutor"};
        String novoTitulo = (String) JOptionPane.showInputDialog(parent, "Novo título:", "Título", JOptionPane.PLAIN_MESSAGE, null, titulos, professor.getTitulo());
        if (novoTitulo == null) return;

        String[] departamentos = {"TI", "Engenharia", "Matemática", "Física", "Química", "Letras"};
        String novoDepto = (String) JOptionPane.showInputDialog(parent, "Novo departamento:", "Departamento", JOptionPane.PLAIN_MESSAGE, null, departamentos, professor.getDepartamento());
        if (novoDepto == null) return;

        professor.setNome(novoNome);
        professor.setTitulo(novoTitulo);
        professor.setDepartamento(novoDepto);

        JOptionPane.showMessageDialog(parent, "Professor atualizado com sucesso.");
    }

    public static void excluirProfessor(JFrame parent) {
        if (Main.docentes.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Nenhum professor cadastrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] professores = Main.docentes.stream()
                .map(p -> p.getCpf() + " - " + p.getNome())
                .toArray(String[]::new);

        String escolha = (String) JOptionPane.showInputDialog(parent, "Selecione o professor para excluir:", "Excluir Professor", JOptionPane.PLAIN_MESSAGE, null, professores, professores[0]);
        if (escolha == null) return;

        String cpf = escolha.split(" - ")[0];
        Professor professor = Main.docentes.stream().filter(p -> p.getCpf().equals(cpf)).findFirst().orElse(null);
        if (professor == null) return;

        int confirmacao = JOptionPane.showConfirmDialog(parent, "Tem certeza que deseja excluir o professor " + professor.getNome() + "?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            Main.docentes.remove(professor);
            JOptionPane.showMessageDialog(parent, "Professor removido com sucesso.");
        }
    }

    public static void exibirProfessores(JFrame parent) {
        if (Main.docentes.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Nenhum professor cadastrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder("Lista de Professores:\n\n");
        for (Professor p : Main.docentes) {
            sb.append("- ").append(p.getNome())
                    .append(" | CPF: ").append(p.getCpf())
                    .append(" | Título: ").append(p.getTitulo())
                    .append(" | Departamento: ").append(p.getDepartamento())
                    .append("\n");
        }

        JOptionPane.showMessageDialog(parent, sb.toString(), "Professores", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void verDisciplinasMinistradas(JFrame parent) {
        if (Main.docentes.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Nenhum professor cadastrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] professores = Main.docentes.stream()
                .map(p -> p.getCpf() + " - " + p.getNome())
                .toArray(String[]::new);

        String escolha = (String) JOptionPane.showInputDialog(parent, "Selecione o professor:", "Disciplinas Ministradas", JOptionPane.PLAIN_MESSAGE, null, professores, professores[0]);
        if (escolha == null) return;

        String cpf = escolha.split(" - ")[0];
        Professor professor = Main.docentes.stream().filter(p -> p.getCpf().equals(cpf)).findFirst().orElse(null);
        if (professor == null) return;

        StringBuilder sb = new StringBuilder("Disciplinas ministradas por " + professor.getNome() + ":\n\n");
        boolean temDisciplinas = false;
        for (Secao s : Main.gradeHorarios.getSecoesOfertadas().values()) {
            if (s.getInstrutor() != null && s.getInstrutor().equals(professor)) {
                temDisciplinas = true;
                sb.append("- ").append(s.getCursoRepresentado().getCodigoCurso())
                        .append(" - ").append(s.getCursoRepresentado().getNomeCurso())
                        .append(" | Seção ").append(s.getNumeroSecao())
                        .append(" | ").append(s.getDiaSemana()).append(" - ").append(s.getHorario())
                        .append(" | Sala: ").append(s.getSala()).append("\n");
            }
        }

        if (!temDisciplinas) {
            sb.append("(Nenhuma disciplina alocada)");
        }

        JOptionPane.showMessageDialog(parent, sb.toString(), "Disciplinas Ministradas", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void alocarProfessorEmSecao(JFrame parent) {
        if (Main.docentes.isEmpty() || Main.gradeHorarios.getSecoesOfertadas().isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Necessário ter professores e seções cadastradas.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] professores = Main.docentes.stream()
                .map(p -> p.getCpf() + " - " + p.getNome())
                .toArray(String[]::new);
        String escolhaProf = (String) JOptionPane.showInputDialog(parent, "Selecione o professor:", "Alocar Professor", JOptionPane.PLAIN_MESSAGE, null, professores, professores[0]);
        if (escolhaProf == null) return;

        String cpf = escolhaProf.split(" - ")[0];
        Professor professor = Main.docentes.stream().filter(p -> p.getCpf().equals(cpf)).findFirst().orElse(null);
        if (professor == null) return;

        var secoesDisponiveis = Main.gradeHorarios.getSecoesOfertadas().values().stream()
                .filter(s -> s.getInstrutor() == null)
                .toList();

        if (secoesDisponiveis.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Todas as seções já têm professor.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] secoes = secoesDisponiveis.stream()
                .map(s -> s.getIdentificadorCompleto() + " - " + s.getCursoRepresentado().getNomeCurso())
                .toArray(String[]::new);
        String escolhaSecao = (String) JOptionPane.showInputDialog(parent, "Selecione a seção:", "Alocar Professor", JOptionPane.PLAIN_MESSAGE, null, secoes, secoes[0]);
        if (escolhaSecao == null) return;

        String cod = escolhaSecao.split(" - ")[0];
        Secao sec = Main.gradeHorarios.localizarSecao(cod);

        professor.aceitarEnsinar(sec);
        JOptionPane.showMessageDialog(parent, "Professor alocado com sucesso.");
    }
}