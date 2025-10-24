package com.example.rbac.repository;

import com.example.rbac.entity.Usuario; // entidade User
import org.springframework.data.jpa.repository.JpaRepository; // JPA repo
import java.util.Optional; // Optional
/*
Interface Repositório do usuário: Repositório responsável por realizar operações de CRUD no banco de dados
 para a entidade User.
 Estende JpaRepository, o que fornece métodos prontos como save(), findAll(), deleteById(), etc.
  Responsabilidades:
  - Buscar usuários por email ou ID.
  - Salvar novos usuários.
  - Atualizar e remover usuários.
 */


public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email); // busca usuário por email
}
