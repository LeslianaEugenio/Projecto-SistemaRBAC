package com.example.rbac.dto;

// DTO para o corpo do login
public class LoginRequest {
    private String email; // email recebido na requisição
    private String senha; // senha recebida


    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
}
