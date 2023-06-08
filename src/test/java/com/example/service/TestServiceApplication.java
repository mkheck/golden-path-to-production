package com.example.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;

@Configuration
public class TestServiceApplication {

    @Bean
    @ServiceConnection
    @RestartScope
    PostgreSQLContainer postgresContainer() {
        return new PostgreSQLContainer("postgres:latest");
    }

    public static void main(String[] args) {
        SpringApplication
                .from(ServiceApplication::main)
                .with(TestServiceApplication.class)
                .run(args);
    }

}
