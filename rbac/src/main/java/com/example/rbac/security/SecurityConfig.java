package com.example.rbac.security;


import com.example.rbac.security.JwtAuthenticationFilter; // filtro JWT
import org.springframework.beans.factory.annotation.Autowired; // injeção
import org.springframework.context.annotation.Bean; // bean
import org.springframework.context.annotation.Configuration; // configuração
import org.springframework.security.authentication.AuthenticationManager; // autenticação
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration; // para obter AuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; // habilita @PreAuthorize
import org.springframework.security.config.http.SessionCreationPolicy; // estateless
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // codificador de senhas
import org.springframework.security.crypto.password.PasswordEncoder; // interface
import org.springframework.security.web.SecurityFilterChain; // filter chain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // filtro padrão

import org.springframework.security.config.annotation.web.builders.HttpSecurity; // builder HttpSecurity

@Configuration // marca classe de configuração
@EnableMethodSecurity // habilita anotações @PreAuthorize e @Secured
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter; // nosso filtro JWT

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // bean para criptografar senhas
    }

    // expõe AuthenticationManager (necessário para autenticação manual no controller)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // configura a cadeia de segurança
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // desabilita CSRF (usado normalmente em APIs stateless)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // API sem sessão
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/auth/**", "/h2-console/**").permitAll() // permite endpoints de auth e console H2
                .anyRequest().authenticated(); // o resto exige autenticação

        // adiciona nosso filtro JWT antes do filtro do Spring para autenticação por usuário/senha
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // necessário para permitir console do H2 (iframe)
        http.headers().frameOptions().disable();

        return http.build(); // constrói o SecurityFilterChain
    }
}


