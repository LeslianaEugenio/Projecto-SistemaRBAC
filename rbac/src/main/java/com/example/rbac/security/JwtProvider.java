package com.example.rbac.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;          // use jakarta com Spring Boot 3+
import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

    // injeta o segredo (esperamos Base64 string via env var ou application.properties)
    @Value("${app.jwt.secret}")
    private String jwtSecret;

    // tempo em milissegundos
    @Value("${app.jwt.expirationMs}")
    private int jwtExpirationMs;

    // chave HMAC derivada do segredo (instanciada em init)
    private Key key;

    @PostConstruct
    public void init() {
        // validação básica para evitar NullPointer e WeakKeyException
        if (jwtSecret == null || jwtSecret.isBlank()) {
            throw new IllegalStateException("JWT secret não definido. Configure APP_JWT_SECRET.");
        }

        // decodifica a string base64 para bytes e cria a Key HMAC-SHA
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        // Keys.hmacShaKeyFor lança WeakKeyException se a chave for muito curta
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // gera token JWT a partir de Authentication (usado após autenticação bem sucedida)
    public String generateToken(Authentication authentication) {
        String username = authentication.getName(); // normalmente email
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(username)            // assunto = username
                .setIssuedAt(now)                // data de emissão
                .setExpiration(expiryDate)       // expiração
                .signWith(key, SignatureAlgorithm.HS256) // assina com a chave HMAC
                .compact();
    }

    // valida token (assinatura/expiração)
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            // token inválido, expirado, malformado, assinatura inválida...
            return false;
        }
    }

    // extrai username (subject) do token
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
