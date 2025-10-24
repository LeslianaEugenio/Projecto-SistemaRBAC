package com.example.rbac.config;


import com.example.rbac.entity.Usuario; // entidade User
import com.example.rbac.repository.UsuarioRepositorio; // repo User
import org.springframework.beans.factory.annotation.Autowired; // injeção
import org.springframework.security.core.GrantedAuthority; // autoridade do Spring
import org.springframework.security.core.authority.SimpleGrantedAuthority; // implementação concreta
import org.springframework.security.core.userdetails.UserDetails; // interface UserDetails
import org.springframework.security.core.userdetails.UserDetailsService; // interface para carregar usuário\\
import org.springframework.security.core.userdetails.UsernameNotFoundException; // exceção se não achar
import org.springframework.stereotype.Service; // marca serviço
import java.util.List;
import java.util.stream.Collectors;

/*
  Classe CustomUserDetailsService: Serviço que carrega informações do usuário a partir do banco de dados
  para o processo de autenticação do Spring Security.
  Responsabilidades:
  - Buscar o usuário pelo nome de usuário (ou email).
  - Converter os dados do usuário em um objeto UserDetails para o Spring.
 */




@Service // registra como bean do Spring
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired // injeta o repositório
    private UsuarioRepositorio userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // busca usuário por email (username)
        Usuario usuario = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com este email: " + username));

        // converte roles para GrantedAuthority exigido pelo Spring Security
        List<GrantedAuthority> authorities = usuario.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getNome()))
                .collect(Collectors.toList());

        // retorna um UserDetails (classe do Spring Security) com dados essenciais
        return new org.springframework.security.core.userdetails.User(
                usuario.getEmail(), // username
                usuario.getSenha(), // senha (já hashed)
                usuario.isActiva(), // habilitado
                true, // conta não expirada
                true, // credencial não expirado
                true, // Conta não bloqueada
                authorities // autorizações (roles)
        );
    }
}

