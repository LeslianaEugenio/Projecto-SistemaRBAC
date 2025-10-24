package com.example.rbac.jwt;

import com.example.rbac.config.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
/*
Classe JwtAuthenticationFilter: Filtro que intercepta todas as requisições HTTP para verificar
  se o token JWT está presente e é válido.
Responsabilidades:
  - Extrair o token do cabeçalho "Authorization".
  - Validar o token usando JwtProvider.
  - Autenticar o usuário no contexto do Spring Security.
 */


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            // extrai header Authorization
            String header = request.getHeader("Authorization");
            String token = null;

            if (header != null && header.startsWith("Bearer ")) {
                token = header.substring(7); // remove "Bearer "
            }

            // se houver token e ele for válido, autentica no SecurityContext
            if (token != null && jwtProvider.validateToken(token)) {
                String username = jwtProvider.getUsernameFromToken(token);

                // carrega UserDetails (pode lançar UsernameNotFoundException - deixa propagar se desejar)
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // monta a autenticação com as autorizações do usuário
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // define a autenticação no contexto de segurança
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            // não interromper a cadeia — só loga (ou trata) e segue em frente
            // Ex.: token malformado, user não encontrado, etc.
            logger.error("Falha ao autenticar o usuário via JWT: {}");
        }

        // prossegue para os próximos filtros/endpoint
        filterChain.doFilter(request, response);
    }
}
