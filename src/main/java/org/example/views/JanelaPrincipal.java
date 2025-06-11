package org.example.views;

import org.example.controllers.AlunoController;
import org.example.controllers.CursoController;
import org.example.controllers.ProfessorController;
import org.example.controllers.RelatorioController;
import org.example.controllers.SecaoController;
import org.example.views.JanelaCadastroUsuario;
import org.example.Main;
import javax.swing.*;
import java.awt.*;

public class JanelaPrincipal extends JFrame {
    public JanelaPrincipal() {
        super("Sistema Escolar - Interface Grafica");
        String perfil = Main.usuarioLogado.getPerfil();
        boolean isAdmin = perfil.equalsIgnoreCase("admin");
        boolean isSecretaria = perfil.equalsIgnoreCase("secretaria");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 700);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Bem-vindo ao Sistema Escolar", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        JTabbedPane abas = new JTabbedPane();

        if (isSecretaria) {
            // Alunos
            JPanel painelAlunos = new JPanel(new GridLayout(0, 1, 10, 10));
            JButton btnVerAlunos = new JButton("Ver Alunos");
            JButton btnNovoAluno = new JButton("Cadastrar Novo Aluno");
            JButton btnEditarAluno = new JButton("Editar Aluno");
            JButton btnTransferirAluno = new JButton("Transferir Aluno de Curso");
            JButton btnMatricularAluno = new JButton("Matricular em Seção");
            JButton btnExcluirAluno = new JButton("Excluir Aluno");
            JButton btnBuscarAluno = new JButton("Buscar Aluno por Nome ou CPF");

            painelAlunos.add(btnVerAlunos);
            painelAlunos.add(btnNovoAluno);
            painelAlunos.add(btnEditarAluno);
            painelAlunos.add(btnTransferirAluno);
            painelAlunos.add(btnMatricularAluno);
            painelAlunos.add(btnBuscarAluno);
            painelAlunos.add(btnExcluirAluno);
            abas.addTab("Alunos", painelAlunos);

            btnNovoAluno.addActionListener(e -> AlunoController.cadastrarAluno(this));
            btnVerAlunos.addActionListener(e -> AlunoController.exibirAlunos(this));
            btnEditarAluno.addActionListener(e -> AlunoController.editarAluno(this));
            btnTransferirAluno.addActionListener(e -> AlunoController.transferirAlunoDeCurso(this));
            btnMatricularAluno.addActionListener(e -> AlunoController.matricularAlunoEmSecao(this));
            btnBuscarAluno.addActionListener(e -> AlunoController.buscarAluno(this));
            btnExcluirAluno.addActionListener(e -> AlunoController.excluirAluno(this));

            // Cursos
            JPanel painelCursos = new JPanel(new GridLayout(0, 1, 10, 10));
            JButton btnVerCursos = new JButton("Ver Cursos");
            JButton btnCadastrarCurso = new JButton("Cadastrar Curso");
            JButton btnEditarCurso = new JButton("Editar Curso");
            JButton btnExcluirCurso = new JButton("Excluir Curso");
            JButton btnGerenciarPreReq = new JButton("Gerenciar Pré-Requisitos");
            painelCursos.add(btnVerCursos);
            painelCursos.add(btnCadastrarCurso);
            painelCursos.add(btnEditarCurso);
            painelCursos.add(btnExcluirCurso);
            painelCursos.add(btnGerenciarPreReq);
            abas.addTab("Cursos", painelCursos);

            btnCadastrarCurso.addActionListener(e -> CursoController.cadastrarCurso(this));
            btnVerCursos.addActionListener(e -> CursoController.exibirCursos(this));
            btnEditarCurso.addActionListener(e -> CursoController.editarCurso(this));
            btnExcluirCurso.addActionListener(e -> CursoController.excluirCurso(this));
            btnGerenciarPreReq.addActionListener(e -> CursoController.gerenciarPreRequisitos(this));

            // Professores
            JPanel painelProfessores = new JPanel(new GridLayout(0, 1, 10, 10));
            JButton btnVerProfessores = new JButton("Ver Professores");
            JButton btnCadastrarProfessor = new JButton("Cadastrar Professor");
            JButton btnEditarProfessor = new JButton("Editar Professor");
            JButton btnExcluirProfessor = new JButton("Excluir Professor");
            JButton btnAlocarProfessor = new JButton("Alocar Professor em Seção");
            JButton btnDisciplinasProfessor = new JButton("Disciplinas por Professor");

            painelProfessores.add(btnVerProfessores);
            painelProfessores.add(btnCadastrarProfessor);
            painelProfessores.add(btnEditarProfessor);
            painelProfessores.add(btnExcluirProfessor);
            painelProfessores.add(btnAlocarProfessor);
            painelProfessores.add(btnDisciplinasProfessor);
            abas.addTab("Professores", painelProfessores);

            btnCadastrarProfessor.addActionListener(e -> ProfessorController.cadastrarProfessor(this));
            btnEditarProfessor.addActionListener(e -> ProfessorController.editarProfessor(this));
            btnExcluirProfessor.addActionListener(e -> ProfessorController.excluirProfessor(this));
            btnVerProfessores.addActionListener(e -> ProfessorController.exibirProfessores(this));
            btnAlocarProfessor.addActionListener(e -> ProfessorController.alocarProfessorEmSecao(this));
            btnDisciplinasProfessor.addActionListener(e -> ProfessorController.verDisciplinasMinistradas(this));

            // Seções
            JPanel painelSecoes = new JPanel(new GridLayout(0, 1, 10, 10));
            JButton btnAdicionarSecao = new JButton("Adicionar Seção");
            JButton btnEditarSecao = new JButton("Editar Seção");
            JButton btnExcluirSecao = new JButton("Excluir Seção");
            JButton btnVerSecoes = new JButton("Ver Seções");

            painelSecoes.add(btnAdicionarSecao);
            painelSecoes.add(btnEditarSecao);
            painelSecoes.add(btnExcluirSecao);
            painelSecoes.add(btnVerSecoes);

            abas.addTab("Seções", painelSecoes);

            btnAdicionarSecao.addActionListener(e -> SecaoController.adicionarSecao(this));
            btnEditarSecao.addActionListener(e -> SecaoController.editarSecao(this));
            btnExcluirSecao.addActionListener(e -> SecaoController.excluirSecao(this));
            btnVerSecoes.addActionListener(e -> SecaoController.exibirSecoes(this));

            // Notas
            JPanel painelNotas = new JPanel(new GridLayout(0, 1, 10, 10));
            JButton btnLancarNota = new JButton("Lançar Nota");
            JButton btnEditarNota = new JButton("Editar Nota");
            JButton btnEmitirBoletim = new JButton("Emitir Boletim (PDF)");
            JButton btnEnviarBoletim = new JButton("Enviar Boletim (Email)");
            painelNotas.add(btnLancarNota);
            painelNotas.add(btnEditarNota);
            painelNotas.add(btnEmitirBoletim);
            painelNotas.add(btnEnviarBoletim);
            abas.addTab("Notas", painelNotas);

            btnLancarNota.addActionListener(e -> AlunoController.lancarNota());
            btnEditarNota.addActionListener(e -> AlunoController.editarNota());
            btnEmitirBoletim.addActionListener(e -> AlunoController.emitirBoletim(this));
            btnEnviarBoletim.addActionListener(e -> AlunoController.enviarBoletimPorEmail(this));

            // Relatórios
            JPanel painelRelatorios = new JPanel(new GridLayout(0, 1, 10, 10));
            JButton btnEstatisticas = new JButton("Ver Estatísticas do Sistema");
            JButton btnGraficoCursos = new JButton("Gráfico de Alunos por Curso");
            JButton btnSalvarDados = new JButton("Salvar Dados em Arquivo");
            JButton btnLogout = new JButton("Logout");

            painelRelatorios.add(btnEstatisticas);
            painelRelatorios.add(btnGraficoCursos);
            painelRelatorios.add(btnSalvarDados);
            painelRelatorios.add(btnLogout);
            abas.addTab("Relatórios", painelRelatorios);

            btnEstatisticas.addActionListener(e -> RelatorioController.exibirEstatisticasSistema(this));
            btnGraficoCursos.addActionListener(e -> RelatorioController.mostrarGraficoPizza(this));
            btnSalvarDados.addActionListener(e -> RelatorioController.salvarDadosEmArquivo(this));
            btnLogout.addActionListener(e -> realizarLogout());

            add(abas, BorderLayout.CENTER);

        } else if (isAdmin) {
            // Apenas painel mínimo com botão de cadastro e logout
            JPanel painelAdmin = new JPanel(new GridLayout(0, 1, 10, 10));
            JButton btnCadastrarUsuario = new JButton("Cadastrar Novo Usuário");
            JButton btnLogout = new JButton("Logout");

            painelAdmin.add(btnCadastrarUsuario);
            painelAdmin.add(btnLogout);

            btnCadastrarUsuario.addActionListener(e -> new JanelaCadastroUsuario());
            btnLogout.addActionListener(e -> realizarLogout());

            add(painelAdmin, BorderLayout.CENTER);
        }

        setVisible(true);
    }

    private void realizarLogout() {
        int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja realmente sair?", "Logout", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            dispose();
            TelaLogin.main(null);
        }
    }

}