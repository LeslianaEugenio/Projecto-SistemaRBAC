package com.example.rbac.dto;

import lombok.Setter;

public class RegisterRequest {
    private String nome; //
    @Setter
    private String email; //
    private String senha; //


    public String getNome() {
        return nome;
    }
    public void setName(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email; }

    public String getSenha() {
        return senha;
    }
    public void setPassword(String senha) {
        this.senha = senha;
    }
}
