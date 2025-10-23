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

@Service // registra bean
public class UsuarioService {

    @Autowired
    private UsuarioRepositorio userRepository; // repo para user

    @Autowired
    private RoleRepositorio roleRepository; // repo para role

    @Autowired
    private PasswordEncoder passwordEncoder; // encoder BCrypt

    // cria novo usuário com role USER por padrão
    public Usuario registerUser(String name, String email, String rawPassword) {
        Usuario user = new Usuario();
        user.setNome(name);
        user.setEmail(email);
        user.setSenha(passwordEncoder.encode(rawPassword)); // hash da senha
        user.setActiva(true);

        // busca role ROLE_USER e atribui
        Role userRole = roleRepository.findByName("ROLE_USUÁRIO")
                .orElseThrow(() -> new RuntimeException("Papel de usuário não encontrado"));
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);

        return userRepository.save(user); // salva no BD
    }

    public Optional<Usuario> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<Usuario> findById(Long id) {
        return userRepository.findById(id);
    }

    // lista todos os usuários
    public java.util.List<Usuario> findAll() {
        return userRepository.findAll();
    }

    // ativa ou desativa usuário
    public Usuario setActiva(Long userId, boolean active) {
        Usuario user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        user.setActiva(active);
        return userRepository.save(user);
    }

    // atribui role a um usuário
    public Usuario addRoleToUser(Long userId, String roleName) {
        Usuario user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new RuntimeException("Role not found"));
        user.getRoles().add(role);
        return userRepository.save(user);
    }
}


