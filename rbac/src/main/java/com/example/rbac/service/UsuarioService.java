package com.example.rbac.service;

import com.example.rbac.entity.Role; // role entidade
import com.example.rbac.entity.Usuario; // usuário entidade
import com.example.rbac.repository.RoleRepositorio; // repo role
import com.example.rbac.repository.UsuarioRepositorio; // repo user
import org.springframework.beans.factory.annotation.Autowired; // injeção
import org.springframework.security.crypto.password.PasswordEncoder; // para hash de senha
import org.springframework.stereotype.Service; // bean de serviço

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
/*
Classe UserService: Serviço responsável pela lógica de negócio relacionada aos usuários.
  Responsabilidades:
  - Criar, atualizar e desativar usuários.
  - Buscar usuários por email ou ID.
  - Integrar com o UserRepository e o RoleRepository.
 */


@Service // registra bean
public class UsuarioService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio; // repo para usuário

    @Autowired
    private RoleRepositorio roleRepositorio; // repo para role

    @Autowired
    private PasswordEncoder passwordCodificada; // codificação BCrypt

    // cria novo usuário com role USER por padrão
    public Usuario registroUsuario(String name, String email, String rawPassword) {
        Usuario usuario = new Usuario();
        usuario.setNome(name);
        usuario.setEmail(email);
        usuario.setSenha(passwordCodificada.encode(rawPassword)); // hash da senha
        usuario.setActiva(true);

        // busca role ROLE_USER e atribui
        Role userRole = roleRepositorio.findByNome("ROLE_USUÁRIO")
                .orElseThrow(() -> new RuntimeException("Papel de usuário não encontrado"));
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        usuario.setRoles(roles);

        return usuarioRepositorio.save(usuario); // salva no BD
    }

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepositorio.findByEmail(email);
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepositorio.findById(id);
    }

    // lista todos os usuários
    public java.util.List<Usuario> findAll() {
        return usuarioRepositorio.findAll();
    }

    // ativa ou desativa usuário
    public Usuario setActiva(Long userId, boolean active) {
        Usuario user = usuarioRepositorio.findById(userId).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        user.setActiva(active);
        return usuarioRepositorio.save(user);
    }

    // atribui role a um usuário
    public Usuario addRoleToUser(Long userId, String roleName) {
        Usuario usuario = usuarioRepositorio.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepositorio.findByNome(roleName).orElseThrow(() -> new RuntimeException("Role not found"));
        usuario.getRoles().add(role);
        return usuarioRepositorio.save(usuario);
    }
}


