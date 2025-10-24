package com.example.rbac.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
/*
  Classe Usuários: Representa um usuário do sistema.
    Contém informações básicas como nome, email, senha e o papel (role) associado.
  Cada usuário pode ter um ou mais papéis que determinam o que ele pode fazer no sistema.
 Responsabilidades:
 - Armazenar dados de identificação e autenticação do usuário.
 - Manter a relação com as roles (papéis).
 - Servir como entidade mapeada no banco de dados via JPA.
 */


@Entity
@Table(name = "usuarios")
@Getter @Setter
//Classe de modelo para usuario
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nome;

    @Column(unique = true, nullable = false)

    private String email;
    private String senha;
    private boolean activa = true;

    @ManyToMany(fetch = FetchType.EAGER) // vários roles por usuário, buscar eager para geral
    @JoinTable(name = "usuario_roles", // tabela de junção
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))

    private Set<Role> roles = new HashSet<>();

    public Usuario() {};

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

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

    public boolean isActiva() {
        return activa;
    }
    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


}
