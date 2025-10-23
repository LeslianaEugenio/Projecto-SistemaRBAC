package com.example.rbac.controller;

import com.example.rbac.dto.JwtResponse; // DTO token
import com.example.rbac.dto.LoginRequest; // DTO login
import com.example.rbac.dto.RegisterRequest; // DTO register
import com.example.rbac.entity.Usuario; // entidade User
import com.example.rbac.security.JwtProvider; // provider JWT
import com.example.rbac.service.UsuarioService; // serviço User
import org.springframework.beans.factory.annotation.Autowired; // injeção
import org.springframework.http.ResponseEntity; // respostas HTTP
import org.springframework.security.authentication.AuthenticationManager; // manager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // token de autenticação
import org.springframework.security.core.Authentication; // autenticação
import org.springframework.web.bind.annotation.*; // anotações REST

@RestController // controlador REST
@RequestMapping("/auth") // rota base /auth
public class  GeralController  {

    @Autowired
    private AuthenticationManager authenticationManager; // para autenticar credenciais

    @Autowired
    private JwtProvider jwtProvider; // para gerar token

    @Autowired
    private UsuarioService userService; // para registrar usuário

    // endpoint POST /auth/register para criar novo usuário
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest body) {
        // cria usuário e retorna 200 com os dados (podes mudar para 201)
        Usuario user = userService.registerUser(body.getNome(), body.getEmail(), body.getSenha());
        return ResponseEntity.ok(user);
    }

    // endpoint POST /auth/login para autenticar e retornar token JWT
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // autentica usando AuthenticationManager; se falhar, é lançada exceção
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha())
        );

        // gera token JWT a partir da autenticação
        String token = jwtProvider.generateToken(authentication);

        // retorna token no corpo
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
