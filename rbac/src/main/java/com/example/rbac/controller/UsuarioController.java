package com.example.rbac.controller;


import com.example.rbac.entity.Usuario; // entidade Usuário
import com.example.rbac.service.UsuarioService; // serviço
import org.springframework.beans.factory.annotation.Autowired; // injeção
import org.springframework.http.ResponseEntity; // ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize; // para @PreAuthorize
import org.springframework.web.bind.annotation.*; // anotações REST
import java.util.List;

/*
  Classe ControleUsuários: Controlador para gerenciamento de usuários.
Responsabilidades:
 - Listar, atualizar e desativar usuários.
 - Atribuir papéis (roles) a usuários.
 - Gerenciar endpoints administrativos.
 */


@RestController
@RequestMapping("/usuarios") // rota base /usuários
public class UsuarioController  {

    @Autowired
    private UsuarioService userService; // serviço para operações

    // apenas usuários com ROLE_ADMIN podem listar usuários
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Usuario>> listAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    // endpoint para ativar/desativar usuário - somente ADMIN
    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Usuario> activate(@PathVariable Long id, @RequestParam boolean active) {
        Usuario user = userService.setActiva(id, active);
        return ResponseEntity.ok(new Usuario());
    }

    // endpoint para adicionar role a um usuário - somente ADMIN
    @PatchMapping("/{id}/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Usuario> addRole(@PathVariable Long id, @RequestParam String role) {
        Usuario user = userService.addRoleToUser(id, role);
        return ResponseEntity.ok(user);
    }
}

