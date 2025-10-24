package com.example.rbac.security;

import com.example.rbac.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired; // para injeção de dependências
import org.springframework.context.annotation.Bean; // usado para criar beans gerenciados pelo Spring
import org.springframework.context.annotation.Configuration; // marca esta classe como uma configuração
import org.springframework.security.authentication.AuthenticationManager; // gerencia a autenticação
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration; // fornece o AuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; // habilita segurança baseada em anotações (@PreAuthorize, @Secured)
import org.springframework.security.config.http.SessionCreationPolicy; // define política de sessão (STATELESS)
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // para encriptar senhas
import org.springframework.security.crypto.password.PasswordEncoder; // interface de codificação
import org.springframework.security.web.SecurityFilterChain; // cadeia de filtros de segurança
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // filtro padrão de autenticação
import org.springframework.security.config.annotation.web.builders.HttpSecurity; // configura segurança HTTP

/**
 * Classe SecurityConfig
 * ------------------------------------------------------------
 * Responsável por toda a configuração de segurança do sistema.
 * Aqui definimos as regras de autenticação e autorização,
 * os endpoints públicos, os filtros JWT e as políticas de sessão.
 * ------------------------------------------------------------
 * Principais responsabilidades:
 * - Configurar o Spring Security para usar JWT;
 * - Definir quais rotas são públicas e quais exigem login;
 * - Registrar o filtro JwtAuthenticationFilter na cadeia de segurança;
 * - Desativar CSRF (porque a aplicação é stateless);
 * - Permitir o acesso ao console do banco H2 em ambiente de desenvolvimento.
 */
@Configuration // indica que esta é uma classe de configuração do Spring
@EnableMethodSecurity // ativa o uso de anotações como @PreAuthorize
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter; // filtro JWT personalizado

    /**
     * Método responsável por criar o bean que criptografa as senhas dos usuários.
     * Aqui usamos o algoritmo BCrypt, que é seguro e amplamente utilizado.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Exposição do AuthenticationManager como bean para ser usado em outras partes da aplicação,
     * como no controller de autenticação (login).
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Método principal que define toda a configuração de segurança HTTP da aplicação.
     * Aqui configuramos o comportamento da autenticação, autorização e filtros.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Desabilita CSRF porque a aplicação usa JWT (não há sessão de navegador)
                .csrf(csrf -> csrf.disable())

                // Define que a aplicação será stateless (sem sessões)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Define quais endpoints são públicos e quais exigem autenticação
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/geral/**", "/h2-console/**").permitAll() // rotas públicas
                        .anyRequest().authenticated() // todas as outras precisam de autenticação
                )

                // Adiciona o filtro JWT antes do filtro padrão do Spring Security
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                // Permite uso de frames do mesmo domínio (necessário para o console H2)
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        // Retorna a configuração construída
        return http.build();
    }
}
