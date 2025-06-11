package org.example.controllers;
import org.example.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import javax.swing.*;
import java.io.File;
import java.io.PrintWriter;
import org.example.models.Curso;
import org.example.models.Aluno;
import org.example.models.Secao;

public class RelatorioController {

    public static void exibirEstatisticasSistema(JFrame parent) {
        int totalAlunos = Main.corpoDiscente.size();
        int totalProfessores = Main.docentes.size();
        int totalCursos = Main.catalogoCursos.size();
        int totalSecoes = Main.gradeHorarios.getSecoesOfertadas().size();
        int totalMatriculas = Main.gradeHorarios.getSecoesOfertadas().values().stream()
                .mapToInt(s -> s.getAlunosMatriculados().size()).sum();

        String texto = "Estatísticas do Sistema:\n\n" +
                "Total de Alunos: " + totalAlunos + "\n" +
                "Total de Professores: " + totalProfessores + "\n" +
                "Total de Cursos: " + totalCursos + "\n" +
                "Total de Seções: " + totalSecoes + "\n" +
                "Total de Matrículas: " + totalMatriculas;

        JOptionPane.showMessageDialog(parent, texto, "Estatísticas", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void mostrarGraficoPizza(JFrame parent) {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        for (Curso curso : Main.catalogoCursos) {
            long quantidade = Main.corpoDiscente.stream()
                    .filter(a -> a.getCurso().equalsIgnoreCase(curso.getNomeCurso()))
                    .count();
            if (quantidade > 0) {
                dataset.setValue(curso.getNomeCurso(), quantidade);
            }
        }

        JFreeChart grafico = ChartFactory.createPieChart(
                "Distribuição de Alunos por Curso",
                dataset,
                true, true, false);

        ChartPanel painelGrafico = new ChartPanel(grafico);
        JFrame frameGrafico = new JFrame("Gráfico de Pizza - Cursos");
        frameGrafico.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameGrafico.setSize(500, 400);
        frameGrafico.setLocationRelativeTo(parent);
        frameGrafico.setContentPane(painelGrafico);
        frameGrafico.setVisible(true);
    }

    public static void salvarDadosEmArquivo(JFrame parent) {
        JFileChooser seletor = new JFileChooser();
        seletor.setDialogTitle("Salvar dados em arquivo");

        int escolha = seletor.showSaveDialog(parent);
        if (escolha != JFileChooser.APPROVE_OPTION) return;

        File arquivo = seletor.getSelectedFile();
        try (PrintWriter writer = new PrintWriter(arquivo)) {
            writer.println("===== SISTEMA ESCOLAR - DADOS SALVOS =====\n");

            writer.println("ALUNOS:");
            for (Aluno a : Main.corpoDiscente) {
                writer.println("- " + a.getNome() + " (" + a.getCpf() + ") - " + a.getCurso() + " - " + a.getGrau());
            }

            writer.println("\nCURSOS:");
            for (Curso c : Main.catalogoCursos) {
                writer.println("- " + c.getCodigoCurso() + ": " + c.getNomeCurso() + " (" + c.getCreditos() + " créditos)");
            }

            writer.println("\nSEÇÕES:");
            for (Secao s : Main.gradeHorarios.getSecoesOfertadas().values()) {
                writer.println("- " + s.getIdentificadorCompleto() + ": " +
                        s.getCursoRepresentado().getNomeCurso() + ", Sala: " + s.getSala() +
                        ", Dia: " + s.getDiaSemana() + ", Horário: " + s.getHorario() +
                        ", Professor: " + (s.getInstrutor() != null ? s.getInstrutor().getNome() : "N/A"));
            }

            writer.println("\nMATRÍCULAS:");
            for (Secao s : Main.gradeHorarios.getSecoesOfertadas().values()) {
                writer.println(s.getIdentificadorCompleto() + ":");
                for (Aluno a : s.getAlunosMatriculados().values()) {
                    writer.println("    - " + a.getNome() + " (" + a.getCpf() + ")");
                }
            }

            writer.flush();
            JOptionPane.showMessageDialog(parent, "Dados salvos com sucesso em:\n" + arquivo.getAbsolutePath());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent, "Erro ao salvar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}