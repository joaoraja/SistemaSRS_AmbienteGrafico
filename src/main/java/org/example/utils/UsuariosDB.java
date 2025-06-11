package org.example.utils;
import org.example.models.Usuario;

import java.util.ArrayList;
import java.util.Optional;
//Simula uma tabela de usuários com autenticação:
public class UsuariosDB {
    private static ArrayList<Usuario> usuarios = new ArrayList<>();

    static {
        // Usuários iniciais
        usuarios.add(new Usuario("admin", "1234", "admin"));
        usuarios.add(new Usuario("secretaria", "1234", "secretaria"));
    }

    public static void adicionarUsuario(Usuario u) {
        usuarios.add(u);
    }

    public static Optional<Usuario> autenticar(String nome, String senha) {
        return usuarios.stream()
                .filter(u -> u.getNome().equals(nome) && u.getSenha().equals(senha))
                .findFirst();
    }

    public static ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }
}