package com.example.rbac.repository;

import com.example.rbac.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface RoleRepositorio extends JpaRepository <Role, Long>{
    Optional<Role> findByName(String nome); //busca role por nome

}
