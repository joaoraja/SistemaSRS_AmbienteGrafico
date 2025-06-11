package org.example.views;
import org.example.Main;
import org.example.utils.UsuariosDB;
import org.example.models.Usuario;

import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {
    public TelaLogin() {
        super("Login do Sistema Escolar");
        Main.carregarDadosIniciais();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 220);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        JPanel painelUsuario = new JPanel(new BorderLayout());
        JLabel lblUsuario = new JLabel("Usuário:");
        JTextField campoUsuario = new JTextField();
        painelUsuario.add(lblUsuario, BorderLayout.NORTH);
        painelUsuario.add(campoUsuario, BorderLayout.CENTER);

        JPanel painelSenha = new JPanel(new BorderLayout());
        JLabel lblSenha = new JLabel("Senha:");
        JPasswordField campoSenha = new JPasswordField();

        // 🔒 Botão Olhinho
        JButton btnMostrarSenha = new JButton("👁");
        btnMostrarSenha.setFocusable(false);
        btnMostrarSenha.setPreferredSize(new Dimension(50, 25));

        final boolean[] visivel = {false};
        btnMostrarSenha.addActionListener(e -> {
            visivel[0] = !visivel[0];
            campoSenha.setEchoChar(visivel[0] ? (char) 0 : '•');
        });

        JPanel painelCampoSenha = new JPanel(new BorderLayout());
        painelCampoSenha.add(campoSenha, BorderLayout.CENTER);
        painelCampoSenha.add(btnMostrarSenha, BorderLayout.EAST);

        painelSenha.add(lblSenha, BorderLayout.NORTH);
        painelSenha.add(painelCampoSenha, BorderLayout.CENTER);

        JButton btnEntrar = new JButton("Entrar");

        add(painelUsuario);
        add(painelSenha);
        add(new JPanel()); // espaço
        add(btnEntrar);

        btnEntrar.addActionListener(e -> {
            String nome = campoUsuario.getText();
            String senha = new String(campoSenha.getPassword());

            var user = UsuariosDB.autenticar(nome, senha);
            if (user.isPresent()) {
                dispose();
                Main.usuarioLogado = user.get(); // Armazena o usuário atual
                new JanelaPrincipal();
            } else {
                JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaLogin::new);
    }
}