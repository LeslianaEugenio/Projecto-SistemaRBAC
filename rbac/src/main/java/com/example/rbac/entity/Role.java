package com.example.rbac.entity;


import jakarta.persistence.*;

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
