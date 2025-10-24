package com.example.rbac;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerPublico {

    @GetMapping("/")
    public String home() {
        return "Servidor Spring Boot está funcionando 🚀";
    }

    @GetMapping("/publico")
    public String publico() {
        return "Rota pública acessível sem autenticação ✅";
    }
}

