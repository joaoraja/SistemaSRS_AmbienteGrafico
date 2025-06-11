package org.example.models;

public class Usuario {
    private String nome;
    private String senha;
    private String perfil; // Ex: "admin", "secretaria"

    public Usuario(String nome, String senha, String perfil) {
        this.nome = nome;
        this.senha = senha;
        this.perfil = perfil;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }

    public String getPerfil() {
        return perfil;
    }

    @Override
    public String toString() {
        return nome + " (" + perfil + ")";
    }
}
