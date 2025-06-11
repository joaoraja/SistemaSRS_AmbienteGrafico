package org.example.controllers;
import org.example.*;
import javax.swing.*;
import org.example.models.Curso;
import org.example.models.Secao;

public class SecaoController {

    public static void adicionarSecao(JFrame parent) {
        if (Main.catalogoCursos.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Nenhum curso cadastrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] cursos = Main.catalogoCursos.stream()
                .map(c -> c.getCodigoCurso() + " - " + c.getNomeCurso())
                .toArray(String[]::new);

        String escolhaCurso = (String) JOptionPane.showInputDialog(parent, "Selecione o curso:", "Adicionar Seção", JOptionPane.PLAIN_MESSAGE, null, cursos, cursos[0]);
        if (escolhaCurso == null) return;

        String codigoCurso = escolhaCurso.split(" - ")[0];
        Curso curso = Main.catalogoCursos.stream().filter(c -> c.getCodigoCurso().equalsIgnoreCase(codigoCurso)).findFirst().orElse(null);
        if (curso == null) return;

        String diaStr = JOptionPane.showInputDialog(parent, "Dia da semana (1 letra):");
        if (diaStr == null || diaStr.length() != 1) return;
        char dia = diaStr.toUpperCase().charAt(0);

        String horario = JOptionPane.showInputDialog(parent, "Horário (ex: 18:00 - 20:00):");
        if (horario == null || horario.isEmpty()) return;

        String sala = JOptionPane.showInputDialog(parent, "Sala:");
        if (sala == null || sala.isEmpty()) return;

        String capacidadeStr = JOptionPane.showInputDialog(parent, "Capacidade:");
        if (capacidadeStr == null || capacidadeStr.isEmpty()) return;

        int capacidade;
        try {
            capacidade = Integer.parseInt(capacidadeStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(parent, "Capacidade inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Secao nova = curso.agendarSecao(dia, horario, sala, capacidade);
        Main.gradeHorarios.adicionarSecao(nova);

        JOptionPane.showMessageDialog(parent, "Seção criada com sucesso.");
    }

    public static void exibirSecoes(JFrame parent) {
        if (Main.gradeHorarios.estaVazia()) {
            JOptionPane.showMessageDialog(parent, "Nenhuma seção cadastrada.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder("Seções Cadastradas:\n\n");
        for (Secao s : Main.gradeHorarios.getSecoesOfertadas().values()) {
            sb.append("- ").append(s.getIdentificadorCompleto())
                    .append(" | Curso: ").append(s.getCursoRepresentado().getNomeCurso())
                    .append(" | Dia: ").append(s.getDiaSemana())
                    .append(" | Horário: ").append(s.getHorario())
                    .append(" | Sala: ").append(s.getSala())
                    .append(" | Capacidade: ").append(s.getCapacidadeAssentos())
                    .append(" | Professor: ").append(s.getInstrutor() != null ? s.getInstrutor().getNome() : "N/A")
                    .append("\n");
        }

        JOptionPane.showMessageDialog(parent, sb.toString(), "Lista de Seções", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void editarSecao(JFrame parent) {
        if (Main.gradeHorarios.estaVazia()) {
            JOptionPane.showMessageDialog(parent, "Nenhuma seção cadastrada.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] secoes = Main.gradeHorarios.getSecoesOfertadas().values().stream()
                .map(s -> s.getIdentificadorCompleto() + " - " + s.getCursoRepresentado().getNomeCurso())
                .toArray(String[]::new);

        String escolha = (String) JOptionPane.showInputDialog(parent, "Selecione a seção:", "Editar Seção", JOptionPane.PLAIN_MESSAGE, null, secoes, secoes[0]);
        if (escolha == null) return;

        String cod = escolha.split(" - ")[0];
        Secao sec = Main.gradeHorarios.localizarSecao(cod);
        if (sec == null) return;

        String novoDia = JOptionPane.showInputDialog(parent, "Novo dia (1 letra):", sec.getDiaSemana());
        if (novoDia == null || novoDia.length() != 1) return;

        String novoHorario = JOptionPane.showInputDialog(parent, "Novo horário:", sec.getHorario());
        if (novoHorario == null || novoHorario.isEmpty()) return;

        String novaSala = JOptionPane.showInputDialog(parent, "Nova sala:", sec.getSala());
        if (novaSala == null || novaSala.isEmpty()) return;

        String novaCapacidadeStr = JOptionPane.showInputDialog(parent, "Nova capacidade:", String.valueOf(sec.getCapacidadeAssentos()));
        if (novaCapacidadeStr == null || novaCapacidadeStr.isEmpty()) return;

        int novaCapacidade;
        try {
            novaCapacidade = Integer.parseInt(novaCapacidadeStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(parent, "Capacidade inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        sec.setDiaSemana(novoDia.toUpperCase().charAt(0));
        sec.setHorario(novoHorario);
        sec.setSala(novaSala);
        sec.setCapacidadeAssentos(novaCapacidade);

        JOptionPane.showMessageDialog(parent, "Seção atualizada com sucesso.");
    }

    public static void excluirSecao(JFrame parent) {
        if (Main.gradeHorarios.estaVazia()) {
            JOptionPane.showMessageDialog(parent, "Nenhuma seção cadastrada.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] secoes = Main.gradeHorarios.getSecoesOfertadas().values().stream()
                .map(s -> s.getIdentificadorCompleto() + " - " + s.getCursoRepresentado().getNomeCurso())
                .toArray(String[]::new);

        String escolha = (String) JOptionPane.showInputDialog(parent, "Selecione a seção para excluir:", "Excluir Seção", JOptionPane.PLAIN_MESSAGE, null, secoes, secoes[0]);
        if (escolha == null) return;

        String cod = escolha.split(" - ")[0];
        Secao sec = Main.gradeHorarios.localizarSecao(cod);
        if (sec == null) return;

        int confirmar = JOptionPane.showConfirmDialog(parent, "Deseja realmente excluir a seção " + cod + "?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirmar != JOptionPane.YES_OPTION) return;

        Main.gradeHorarios.getSecoesOfertadas().remove(cod);
        JOptionPane.showMessageDialog(parent, "Seção excluída com sucesso.");
    }
}