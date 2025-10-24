package com.example.rbac.repository;

import com.example.rbac.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
/*
Interface RoleRepository: Repositório responsável por manipular os dados das roles (papéis).
  Responsabilidades:
  - Buscar roles por nome.
  - Salvar e atualizar papéis no banco de dados.
 */



public interface RoleRepositorio extends JpaRepository <Role, Long>{
    Optional<Role> findByNome(String nome); //busca role por nome

}
