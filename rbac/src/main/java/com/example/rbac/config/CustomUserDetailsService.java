package com.example.rbac.config;


import com.example.rbac.entity.Usuario; // entidade User
import com.example.rbac.repository.UsuarioRepositorio; // repo User
import org.springframework.beans.factory.annotation.Autowired; // injeção
import org.springframework.security.core.GrantedAuthority; // autoridade do Spring
import org.springframework.security.core.authority.SimpleGrantedAuthority; // implementação concreta
import org.springframework.security.core.userdetails.UserDetails; // interface UserDetails
import org.springframework.security.core.userdetails.UserDetailsService; // interface para carregar user
import org.springframework.security.core.userdetails.UsernameNotFoundException; // exceção se não achar
import org.springframework.stereotype.Service; // marca serviço

import java.util.List;
import java.util.stream.Collectors;

@Service // registra como bean do Spring
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired // injeta repository
    private UsuarioRepositorio userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // busca usuário por email (username)
        Usuario user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        // converte roles para GrantedAuthority exigido pelo Spring Security
        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getNome()))
                .collect(Collectors.toList());

        // retorna um UserDetails (classe do Spring Security) com dados essenciais
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), // username
                user.getSenha(), // senha (já hashed)
                user.isActiva(), // enabled
                true, // accountNonExpired
                true, // credentialsNonExpired
                true, // accountNonLocked
                authorities // authorities (roles)
        );
    }
}

