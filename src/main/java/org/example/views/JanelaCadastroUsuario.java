package org.example.views;

import org.example.models.Usuario;
import org.example.utils.UsuariosDB;
import javax.swing.*;
import java.awt.*;

public class JanelaCadastroUsuario extends JFrame {

    public JanelaCadastroUsuario() {
        super("Cadastro de Novo Usuário");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));

        JLabel lblNome = new JLabel("Nome do Usuário:");
        JTextField campoNome = new JTextField();

        JLabel lblSenha = new JLabel("Senha:");
        JPasswordField campoSenha = new JPasswordField();

        JLabel lblPerfil = new JLabel("Perfil:");
        String[] perfis = {"admin", "secretaria"};
        JComboBox<String> comboPerfil = new JComboBox<>(perfis);

        JButton btnSalvar = new JButton("Cadastrar");

        add(lblNome);      add(campoNome);
        add(lblSenha);     add(campoSenha);
        add(lblPerfil);    add(comboPerfil);
        add(new JLabel()); add(btnSalvar);

        btnSalvar.addActionListener(e -> {
            String nome = campoNome.getText().trim();
            String senha = new String(campoSenha.getPassword()).trim();
            String perfil = (String) comboPerfil.getSelectedItem();

            if (nome.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            UsuariosDB.adicionarUsuario(new Usuario(nome, senha, perfil));
            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
            dispose(); // fecha a janela
        });

        setVisible(true);
    }
}
