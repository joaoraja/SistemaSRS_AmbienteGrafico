package org.example.controllers;
import org.example.*;
import javax.swing.*;
import org.example.models.Curso;

public class CursoController {

    public static void cadastrarCurso(JFrame parent) {
        String codigo = JOptionPane.showInputDialog(parent, "Código do curso:");
        if (codigo == null || codigo.isEmpty()) return;

        boolean codigoJaExiste = Main.catalogoCursos.stream().anyMatch(c -> c.getCodigoCurso().equalsIgnoreCase(codigo));
        if (codigoJaExiste) {
            JOptionPane.showMessageDialog(parent, "Código já cadastrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nome = JOptionPane.showInputDialog(parent, "Nome do curso:");
        if (nome == null || nome.isEmpty()) return;

        String creditosStr = JOptionPane.showInputDialog(parent, "Quantidade de créditos:");
        if (creditosStr == null || creditosStr.isEmpty()) return;

        double creditos;
        try {
            creditos = Double.parseDouble(creditosStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(parent, "Valor de créditos inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Curso novo = new Curso(codigo, nome, creditos);
        Main.catalogoCursos.add(novo);
        JOptionPane.showMessageDialog(parent, "Curso cadastrado com sucesso.");
    }

    public static void exibirCursos(JFrame parent) {
        if (Main.catalogoCursos.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Nenhum curso cadastrado.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder("Cursos Cadastrados:\n\n");
        for (Curso curso : Main.catalogoCursos) {
            sb.append("- ").append(curso.getCodigoCurso())
                    .append(" - ").append(curso.getNomeCurso())
                    .append(" (").append(curso.getCreditos()).append(" créditos)\n");
        }

        JOptionPane.showMessageDialog(parent, sb.toString(), "Lista de Cursos", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void editarCurso(JFrame parent) {
        if (Main.catalogoCursos.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Nenhum curso cadastrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] cursos = Main.catalogoCursos.stream()
                .map(c -> c.getCodigoCurso() + " - " + c.getNomeCurso())
                .toArray(String[]::new);

        String escolha = (String) JOptionPane.showInputDialog(parent, "Selecione o curso:", "Editar Curso", JOptionPane.PLAIN_MESSAGE, null, cursos, cursos[0]);
        if (escolha == null) return;

        String codigo = escolha.split(" - ")[0];
        Curso curso = Main.catalogoCursos.stream().filter(c -> c.getCodigoCurso().equalsIgnoreCase(codigo)).findFirst().orElse(null);
        if (curso == null) return;

        String novoNome = JOptionPane.showInputDialog(parent, "Novo nome do curso:", curso.getNomeCurso());
        if (novoNome == null || novoNome.isEmpty()) return;

        String creditosStr = JOptionPane.showInputDialog(parent, "Nova quantidade de créditos:", String.valueOf(curso.getCreditos()));
        if (creditosStr == null || creditosStr.isEmpty()) return;

        double novosCreditos;
        try {
            novosCreditos = Double.parseDouble(creditosStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(parent, "Valor de créditos inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        curso.setNomeCurso(novoNome);
        curso.setCreditos(novosCreditos);

        JOptionPane.showMessageDialog(parent, "Curso atualizado com sucesso.");
    }

    public static void excluirCurso(JFrame parent) {
        if (Main.catalogoCursos.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Nenhum curso cadastrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] cursos = Main.catalogoCursos.stream()
                .map(c -> c.getCodigoCurso() + " - " + c.getNomeCurso())
                .toArray(String[]::new);

        String escolha = (String) JOptionPane.showInputDialog(parent, "Selecione o curso para excluir:", "Excluir Curso", JOptionPane.PLAIN_MESSAGE, null, cursos, cursos[0]);
        if (escolha == null) return;

        String codigo = escolha.split(" - ")[0];
        Curso curso = Main.catalogoCursos.stream().filter(c -> c.getCodigoCurso().equalsIgnoreCase(codigo)).findFirst().orElse(null);
        if (curso == null) return;

        int confirmacao = JOptionPane.showConfirmDialog(parent, "Deseja realmente excluir o curso " + curso.getNomeCurso() + "?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            Main.catalogoCursos.remove(curso);
            JOptionPane.showMessageDialog(parent, "Curso removido com sucesso.");
        }
    }

    public static void gerenciarPreRequisitos(JFrame parent) {
        if (Main.catalogoCursos.size() < 2) {
            JOptionPane.showMessageDialog(parent, "É necessário ao menos dois cursos cadastrados.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] cursos = Main.catalogoCursos.stream()
                .map(c -> c.getCodigoCurso() + " - " + c.getNomeCurso())
                .toArray(String[]::new);

        String escolhaCurso = (String) JOptionPane.showInputDialog(parent, "Selecione o curso alvo:", "Gerenciar Pré-requisitos", JOptionPane.PLAIN_MESSAGE, null, cursos, cursos[0]);
        if (escolhaCurso == null) return;

        String codigo = escolhaCurso.split(" - ")[0];
        Curso curso = Main.catalogoCursos.stream().filter(c -> c.getCodigoCurso().equalsIgnoreCase(codigo)).findFirst().orElse(null);
        if (curso == null) return;

        var opcoes = Main.catalogoCursos.stream()
                .filter(c -> !c.equals(curso))
                .map(c -> c.getCodigoCurso() + " - " + c.getNomeCurso())
                .toArray(String[]::new);

        String escolha = (String) JOptionPane.showInputDialog(parent, "Escolha um curso para adicionar como pré-requisito:", "Adicionar Pré-requisito", JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]);
        if (escolha == null) return;

        String codPre = escolha.split(" - ")[0];
        Curso preRequisito = Main.catalogoCursos.stream().filter(c -> c.getCodigoCurso().equalsIgnoreCase(codPre)).findFirst().orElse(null);

        if (preRequisito == null || curso.getPreRequisitos().contains(preRequisito)) {
            JOptionPane.showMessageDialog(parent, "Curso já é pré-requisito ou inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        curso.adicionarPreRequisito(preRequisito);
        JOptionPane.showMessageDialog(parent, "Pré-requisito adicionado com sucesso.");
    }
}
