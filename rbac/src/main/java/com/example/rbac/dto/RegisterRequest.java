package com.example.rbac.dto;

import lombok.Setter;
/*DTO que representa os dados enviados para registrar um novo usuário.
  Campos:
  - nome
  - email
  - senha
  - roles (opcional)
 */


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
