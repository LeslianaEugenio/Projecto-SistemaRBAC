package com.example.rbac;


import com.example.rbac.entity.Role; // entidade Role
import com.example.rbac.repository.RoleRepositorio; // repo role
import org.springframework.boot.CommandLineRunner; // interface para rodar no startup
import org.springframework.context.annotation.Bean; // bean
import org.springframework.context.annotation.Configuration; // config

/*
  Classe DataInitializer: Responsável por inicializar dados básicos no sistema logo que a aplicação é executada.
  É usada para criar automaticamente papéis (roles) e usuários padrão no banco de dados.
 Responsabilidades:
  - Verificar se existem papéis cadastrados no banco (ADMIN, USER, etc.);
  - Criar os papéis padrão caso não existam;
  - Criar um usuário administrador inicial com credenciais definidas no código
    (ou via variáveis de ambiente, em projetos mais avançados);
  - Garantir que o sistema tenha dados mínimos para funcionar logo na primeira execução;
  - Facilitar o ambiente de testes e desenvolvimento.
  Observação:
  Esta classe normalmente é anotada com @Component ou @Configuration
  e implementa CommandLineRunner para que o código de inicialização
  seja executado automaticamente após o contexto do Spring Boot ser carregado.
 */


@Configuration // classe de configuração
public class DadosIniciais {

    @Bean
    public CommandLineRunner loadRoles(RoleRepositorio roleRepository) {
        return args -> {
            // insere ROLE_ADMIN se não existir
            roleRepository.findByNome("ROLE_ADMIN").orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));
            // insere ROLE_USER se não existir
            roleRepository.findByNome("ROLE_USER").orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));
        };
    }
}

