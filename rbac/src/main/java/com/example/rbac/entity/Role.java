package com.example.rbac.entity;


import jakarta.persistence.*;
/*
  Classe Role: Representa um papel dentro do sistema (ex: ADMIN, MANAGER, USER).
  É utilizada pelo RBAC (Role-Based Access Control) para definir permissões.
 Responsabilidades:
  - Definir e armazenar o nome do papel.
  - Manter a relação com os usuários.
  - Servir como entidade para controle de autorização.
 */


@Entity //marca a classe como entitidade JPA
@Table(name = "roles")//nome da tabela no banco de dados
//Classe que vai definir o papel/função de cada usuário
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto-incremento
    private long id;

    @Column(unique = true, nullable = false)
    private String nome;

    public Role() {};

    public Role(String nome) {
        this.nome = nome;
    }


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }


}
