package com.example.rbac;


import com.example.rbac.entity.Role; // entidade Role
import com.example.rbac.repository.RoleRepositorio; // repo role
import org.springframework.boot.CommandLineRunner; // interface para rodar no startup
import org.springframework.context.annotation.Bean; // bean
import org.springframework.context.annotation.Configuration; // config

@Configuration // classe de configuração
public class DadosIniciais {

    @Bean
    public CommandLineRunner loadRoles(RoleRepositorio roleRepository) {
        return args -> {
            // insere ROLE_ADMIN se não existir
            roleRepository.findByName("ROLE_ADMIN").orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));
            // insere ROLE_USER se não existir
            roleRepository.findByName("ROLE_USER").orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));
        };
    }
}

