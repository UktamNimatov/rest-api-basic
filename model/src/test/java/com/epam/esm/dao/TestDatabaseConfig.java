package com.epam.esm.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;

@Configuration
@ComponentScan("com.epam.esm")
public class TestDatabaseConfig {
    private static final String SQL_DATABASE = "classpath:embedded.sql";

    @Bean
    public EmbeddedDatabase embeddedDatabase() {

    }
}
