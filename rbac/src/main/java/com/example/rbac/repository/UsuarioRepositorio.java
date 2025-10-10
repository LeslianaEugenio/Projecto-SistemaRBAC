package com.example.rbac.repository;

import com.example.rbac.entity.Usuario; // entidade User
import org.springframework.data.jpa.repository.JpaRepository; // JPA repo
import java.util.Optional; // Optional

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email); // busca usuário por email
}
