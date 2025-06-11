package org.example.controllers;
import org.example.models.Aluno;
import org.example.models.Curso;
import org.example.Main;
import javax.swing.*;
import java.util.List;
import org.example.models.Secao;
import org.example.models.RegistroHistorico;
import javax.swing.JFileChooser;
import java.io.File;
import org.example.utils.ExportadorPDF;
import org.example.utils.EmailService;
import org.example.utils.Validador;


public class AlunoController {

    public static void lancarNota() {
        if (Main.corpoDiscente.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum aluno cadastrado.");
            return;
        }

        String[] alunos = Main.corpoDiscente.stream()
                .map(a -> a.getCpf() + " - " + a.getNome())
                .toArray(String[]::new);

        String escolha = (String) JOptionPane.showInputDialog(null, "Escolha o aluno:", "Lançar Nota",
                JOptionPane.PLAIN_MESSAGE, null, alunos, alunos[0]);
        if (escolha == null) return;

        String cpf = escolha.split(" - ")[0];
        Aluno aluno = Main.corpoDiscente.stream().filter(a -> a.getCpf().equals(cpf)).findFirst().orElse(null);
        if (aluno == null) return;

        List<Secao> secoesSemNota = aluno.getSecoesMatriculadas().stream()
                .filter(s -> s.consultarNota(aluno) == null)
                .toList();

        if (secoesSemNota.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Este aluno já tem nota em todas as seções.");
            return;
        }

        String[] secoes = secoesSemNota.stream()
                .map(s -> s.getIdentificadorCompleto() + " - " + s.getCursoRepresentado().getNomeCurso())
                .toArray(String[]::new);

        String escolhaSecao = (String) JOptionPane.showInputDialog(null, "Escolha a seção:", "Lançar Nota",
                JOptionPane.PLAIN_MESSAGE, null, secoes, secoes[0]);
        if (escolhaSecao == null) return;

        String cod = escolhaSecao.split(" - ")[0];
        Secao sec = Main.gradeHorarios.localizarSecao(cod);
        if (sec == null) return;

        String nota = JOptionPane.showInputDialog(null, "Digite a nota (ex: A, B+):");
        if (nota == null || nota.isBlank()) return;

        if (!RegistroHistorico.validarNota(nota)) {
            JOptionPane.showMessageDialog(null, "Nota inválida.");
            return;
        }

        sec.lancarNota(aluno, nota);
        JOptionPane.showMessageDialog(null, "Nota lançada com sucesso.");
    }

    public static void enviarBoletimPorEmail(JFrame parent) {
        if (Main.corpoDiscente.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Nenhum aluno cadastrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] alunos = Main.corpoDiscente.stream()
                .map(a -> a.getCpf() + " - " + a.getNome())
                .toArray(String[]::new);

        String escolha = (String) JOptionPane.showInputDialog(parent, "Selecione o aluno:", "Enviar Boletim por Email", JOptionPane.PLAIN_MESSAGE, null, alunos, alunos[0]);
        if (escolha == null) return;

        String cpf = escolha.split(" - ")[0];
        Aluno aluno = Main.corpoDiscente.stream()
                .filter(a -> a.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
        if (aluno == null) return;

        String email = JOptionPane.showInputDialog(parent, "Digite o e-mail do aluno:");
        if (email == null || email.isBlank()) return;

        try {
            // Gera o arquivo PDF temporariamente
            File pdf = ExportadorPDF.gerarBoletimPDF(aluno);
            if (pdf != null && pdf.exists()) {
                EmailService.enviarBoletim(email, pdf);
                JOptionPane.showMessageDialog(parent, "Boletim enviado com sucesso para " + email);
            } else {
                JOptionPane.showMessageDialog(parent, "Falha ao gerar o boletim.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent, "Erro ao enviar e-mail:\n" + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void editarNota() {
        if (Main.corpoDiscente.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum aluno cadastrado.");
            return;
        }

        String[] alunos = Main.corpoDiscente.stream()
                .map(a -> a.getCpf() + " - " + a.getNome())
                .toArray(String[]::new);

        String escolhaAluno = (String) JOptionPane.showInputDialog(null, "Escolha o aluno:", "Editar Nota",
                JOptionPane.PLAIN_MESSAGE, null, alunos, alunos[0]);
        if (escolhaAluno == null) return;

        String cpf = escolhaAluno.split(" - ")[0];
        Aluno aluno = Main.corpoDiscente.stream().filter(a -> a.getCpf().equals(cpf)).findFirst().orElse(null);
        if (aluno == null) return;

        List<Secao> secoesComNota = aluno.getSecoesMatriculadas().stream()
                .filter(s -> s.consultarNota(aluno) != null)
                .toList();

        if (secoesComNota.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Este aluno não possui notas lançadas.");
            return;
        }

        String[] secoes = secoesComNota.stream()
                .map(s -> s.getIdentificadorCompleto() + " - " + s.getCursoRepresentado().getNomeCurso())
                .toArray(String[]::new);

        String escolhaSecao = (String) JOptionPane.showInputDialog(null, "Escolha a seção:", "Editar Nota",
                JOptionPane.PLAIN_MESSAGE, null, secoes, secoes[0]);
        if (escolhaSecao == null) return;

        String cod = escolhaSecao.split(" - ")[0];
        Secao sec = Main.gradeHorarios.localizarSecao(cod);

        String notaAtual = sec.consultarNota(aluno);
        String novaNota = JOptionPane.showInputDialog(null, "Nova nota:", notaAtual);
        if (novaNota == null || novaNota.isBlank()) return;

        if (!RegistroHistorico.validarNota(novaNota)) {
            JOptionPane.showMessageDialog(null, "Nota inválida.");
            return;
        }

        RegistroHistorico rh = sec.getNotasAtribuidas().get(aluno);
        rh.setNota(novaNota);
        JOptionPane.showMessageDialog(null, "Nota atualizada com sucesso.");
    }

    public static void emitirBoletim(JFrame parent) {
        if (Main.corpoDiscente.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Nenhum aluno cadastrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] alunos = Main.corpoDiscente.stream()
                .map(a -> a.getCpf() + " - " + a.getNome())
                .toArray(String[]::new);

        String escolha = (String) JOptionPane.showInputDialog(parent, "Selecione o aluno:", "Emitir Boletim",
                JOptionPane.PLAIN_MESSAGE, null, alunos, alunos[0]);

        if (escolha == null) return;

        String cpf = escolha.split(" - ")[0];
        Aluno aluno = Main.corpoDiscente.stream()
                .filter(a -> a.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);

        if (aluno == null) return;

        JFileChooser seletor = new JFileChooser();
        seletor.setDialogTitle("Salvar Boletim como PDF");

        int resultado = seletor.showSaveDialog(parent);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File arquivo = seletor.getSelectedFile();
            ExportadorPDF.exportarBoletim(aluno, arquivo.getAbsolutePath() + ".pdf");
            JOptionPane.showMessageDialog(parent, "Boletim exportado com sucesso!");
        }
    }

    public static void cadastrarAluno(JFrame parent) {
        String nome = JOptionPane.showInputDialog(parent, "Nome do aluno:");
        if (nome == null || nome.isEmpty()) return;

        String cpf = JOptionPane.showInputDialog(parent, "CPF do aluno:");
        if (cpf == null || cpf.isEmpty()) return;

        if (!Validador.validarCPF(cpf)) {
            JOptionPane.showMessageDialog(parent, "CPF inválido. Use o formato 000.000.000-00", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean cpfJaExiste = Main.corpoDiscente.stream().anyMatch(a -> a.getCpf().equals(cpf));

        if (Main.catalogoCursos.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Nenhum curso disponível. Cadastre um curso primeiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] opcoesCursos = Main.catalogoCursos.stream()
                .map(Curso::getNomeCurso)
                .toArray(String[]::new);

        String curso = (String) JOptionPane.showInputDialog(parent, "Selecione o curso:", "Curso",
                JOptionPane.PLAIN_MESSAGE, null, opcoesCursos, opcoesCursos[0]);
        if (curso == null) return;

        String[] opcoesGraus = {"Bacharelado", "Licenciatura", "Tecnólogo", "Pós-graduação"};
        String grau = (String) JOptionPane.showInputDialog(parent, "Selecione o grau:", "Grau",
                JOptionPane.PLAIN_MESSAGE, null, opcoesGraus, opcoesGraus[0]);
        if (grau == null) return;

        Aluno novo = new Aluno(nome, cpf, curso, grau);
        Main.corpoDiscente.add(novo);
        JOptionPane.showMessageDialog(parent, "Aluno cadastrado com sucesso.");
    }

    public static void matricularAlunoEmSecao(JFrame parent) {
        if (Main.corpoDiscente.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Nenhum aluno cadastrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] alunos = Main.corpoDiscente.stream()
                .map(a -> a.getCpf() + " - " + a.getNome())
                .toArray(String[]::new);

        String escolhaAluno = (String) JOptionPane.showInputDialog(parent, "Selecione o aluno:", "Matrícula", JOptionPane.PLAIN_MESSAGE, null, alunos, alunos[0]);
        if (escolhaAluno == null) return;

        String cpf = escolhaAluno.split(" - ")[0];
        Aluno aluno = Main.corpoDiscente.stream().filter(a -> a.getCpf().equals(cpf)).findFirst().orElse(null);
        if (aluno == null) return;

        var secoesDisponiveis = Main.gradeHorarios.getSecoesOfertadas().values().stream()
                .filter(s -> !aluno.getSecoesMatriculadas().contains(s) && s.getAlunosMatriculados().size() < s.getCapacidadeAssentos())
                .filter(s -> Main.catalogoCursos.stream()
                        .filter(c -> c.getNomeCurso().equalsIgnoreCase(s.getCursoRepresentado().getNomeCurso()))
                        .flatMap(c -> c.getPreRequisitos().stream())
                        .allMatch(pr -> aluno.getHistorico().getRegistros().stream()
                                .anyMatch(rh -> rh.getSecao().getCursoRepresentado().equals(pr) && !rh.getNota().equals("F"))))
                .toList();

        if (secoesDisponiveis.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Nenhuma seção disponível para este aluno.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] secoes = secoesDisponiveis.stream()
                .map(s -> s.getIdentificadorCompleto() + " - " + s.getCursoRepresentado().getNomeCurso())
                .toArray(String[]::new);

        String escolhaSecao = (String) JOptionPane.showInputDialog(parent, "Escolha a seção:", "Matrícula", JOptionPane.PLAIN_MESSAGE, null, secoes, secoes[0]);
        if (escolhaSecao == null) return;

        String codSecao = escolhaSecao.split(" - ")[0];
        Secao secao = Main.gradeHorarios.localizarSecao(codSecao);

        if (secao != null && secao.matricularAluno(aluno)) {
            JOptionPane.showMessageDialog(parent, "Aluno matriculado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(parent, "Erro ao matricular aluno.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void exibirAlunos(JFrame parent) {
        if (Main.corpoDiscente.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Nenhum aluno cadastrado.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder("Alunos Cadastrados:\n\n");
        for (Aluno aluno : Main.corpoDiscente) {
            sb.append("- ").append(aluno.getNome())
                    .append(" (CPF: ").append(aluno.getCpf()).append(") | Curso: ")
                    .append(aluno.getCurso()).append(" (").append(aluno.getGrau()).append(")\n");
        }

        JOptionPane.showMessageDialog(parent, sb.toString(), "Lista de Alunos", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void editarAluno(JFrame parent) {
        if (Main.corpoDiscente.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Nenhum aluno cadastrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] alunos = Main.corpoDiscente.stream()
                .map(a -> a.getCpf() + " - " + a.getNome())
                .toArray(String[]::new);

        String escolha = (String) JOptionPane.showInputDialog(parent, "Selecione o aluno:", "Editar Aluno", JOptionPane.PLAIN_MESSAGE, null, alunos, alunos[0]);
        if (escolha == null) return;

        String cpf = escolha.split(" - ")[0];
        Aluno aluno = Main.corpoDiscente.stream().filter(a -> a.getCpf().equals(cpf)).findFirst().orElse(null);
        if (aluno == null) return;

        String novoNome = JOptionPane.showInputDialog(parent, "Novo nome:", aluno.getNome());
        if (novoNome == null || novoNome.isEmpty()) return;

        String[] opcoesCursos = Main.catalogoCursos.stream()
                .map(Curso::getNomeCurso)
                .toArray(String[]::new);
        String novoCurso = (String) JOptionPane.showInputDialog(parent, "Novo curso:", "Curso", JOptionPane.PLAIN_MESSAGE, null, opcoesCursos, aluno.getCurso());
        if (novoCurso == null) return;

        String[] opcoesGraus = {"Bacharelado", "Tecnólogo", "Licenciatura", "Pós-graduação"};
        String novoGrau = (String) JOptionPane.showInputDialog(parent, "Novo grau:", "Grau", JOptionPane.PLAIN_MESSAGE, null, opcoesGraus, aluno.getGrau());
        if (novoGrau == null) return;

        aluno.setNome(novoNome);
        aluno.setCurso(novoCurso);
        aluno.setGrau(novoGrau);

        JOptionPane.showMessageDialog(parent, "Aluno atualizado com sucesso.");
    }

    public static void excluirAluno(JFrame parent) {
        if (Main.corpoDiscente.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Nenhum aluno cadastrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] alunos = Main.corpoDiscente.stream()
                .map(a -> a.getCpf() + " - " + a.getNome())
                .toArray(String[]::new);

        String escolha = (String) JOptionPane.showInputDialog(parent, "Selecione o aluno para excluir:", "Excluir Aluno", JOptionPane.PLAIN_MESSAGE, null, alunos, alunos[0]);
        if (escolha == null) return;

        String cpf = escolha.split(" - ")[0];
        Aluno aluno = Main.corpoDiscente.stream().filter(a -> a.getCpf().equals(cpf)).findFirst().orElse(null);
        if (aluno == null) return;

        int confirmacao = JOptionPane.showConfirmDialog(parent, "Tem certeza que deseja excluir o aluno " + aluno.getNome() + "?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            Main.corpoDiscente.remove(aluno);
            JOptionPane.showMessageDialog(parent, "Aluno removido com sucesso.");
        }
    }

    public static void transferirAlunoDeCurso(JFrame parent) {
        if (Main.corpoDiscente.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Nenhum aluno cadastrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] alunos = Main.corpoDiscente.stream()
                .map(a -> a.getCpf() + " - " + a.getNome())
                .toArray(String[]::new);

        String escolha = (String) JOptionPane.showInputDialog(parent, "Selecione o aluno:", "Transferir de Curso", JOptionPane.PLAIN_MESSAGE, null, alunos, alunos[0]);
        if (escolha == null) return;

        String cpf = escolha.split(" - ")[0];
        Aluno aluno = Main.corpoDiscente.stream().filter(a -> a.getCpf().equals(cpf)).findFirst().orElse(null);
        if (aluno == null) return;

        String[] opcoesCursos = Main.catalogoCursos.stream()
                .map(Curso::getNomeCurso)
                .toArray(String[]::new);
        String novoCurso = (String) JOptionPane.showInputDialog(parent, "Novo curso:", "Curso", JOptionPane.PLAIN_MESSAGE, null, opcoesCursos, aluno.getCurso());
        if (novoCurso == null) return;

        String[] opcoesGraus = {"Bacharelado", "Tecnólogo", "Licenciatura", "Pós-graduação"};
        String novoGrau = (String) JOptionPane.showInputDialog(parent, "Novo grau:", "Grau", JOptionPane.PLAIN_MESSAGE, null, opcoesGraus, aluno.getGrau());
        if (novoGrau == null) return;

        aluno.setCurso(novoCurso);
        aluno.setGrau(novoGrau);

        JOptionPane.showMessageDialog(parent, "Curso do aluno atualizado com sucesso.");
    }

    public static void buscarAluno(JFrame parent) {
        if (Main.corpoDiscente.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Nenhum aluno cadastrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String termoInput = JOptionPane.showInputDialog(parent, "Digite nome ou CPF para buscar:");
        if (termoInput == null || termoInput.isEmpty()) return;

        final String termo = termoInput.toLowerCase();

        List<Aluno> resultados = Main.corpoDiscente.stream()
                .filter(a -> a.getNome().toLowerCase().contains(termo) || a.getCpf().contains(termo))
                .toList();

        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Nenhum aluno encontrado.", "Busca", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder("Resultados encontrados:\n\n");
        for (Aluno a : resultados) {
            sb.append("- ").append(a.getNome()).append(" (").append(a.getCpf()).append(")\n");
        }

        JOptionPane.showMessageDialog(parent, sb.toString(), "Busca", JOptionPane.INFORMATION_MESSAGE);
    }
}