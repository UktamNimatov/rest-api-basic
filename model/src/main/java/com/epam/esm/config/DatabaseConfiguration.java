package com.epam.esm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Profile("prod")
@Configuration
public class DatabaseConfiguration {
    private static final Logger logger = LogManager.getLogger();

    private static final String FILE_PATH = "/config/database.properties";
    private static final String DATABASE_DRIVER_CLASS = "spring.datasource.driver-class-name";
    private static final String DATABASE_URL = "spring.datasource.url";
    private static final String DATABASE_USERNAME = "spring.datasource.username";
    private static final String DATABASE_PASSWORD = "spring.datasource.password";
    private static final String DATABASE_MAX_POOL_SIZE = "spring.datasource.maxPoolSize";

    @Bean
    public HikariConfig getHikariConfig() {
        Properties properties = new Properties();
        InputStream inputStream = DatabaseConfiguration.class.getClassLoader()
                .getResourceAsStream(FILE_PATH);
        try {
            properties.load(inputStream);
        } catch (IOException exception) {
            logger.error("error in loading driver class or class not found", exception);
            throw new ExceptionInInitializerError(exception);
        }

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(properties.getProperty(DATABASE_DRIVER_CLASS));
        hikariConfig.setJdbcUrl(properties.getProperty(DATABASE_URL));
        hikariConfig.setUsername(properties.getProperty(DATABASE_USERNAME));
        hikariConfig.setPassword(properties.getProperty(DATABASE_PASSWORD));
        hikariConfig.setMaximumPoolSize(Integer.parseInt(properties.getProperty(DATABASE_MAX_POOL_SIZE)));
        return hikariConfig;
    }



    @Bean
    public DataSource dataSource(HikariConfig hikariConfig) {
        logger.info(">?>?>?>?>?>?");
        logger.info(hikariConfig.getDriverClassName());
        logger.info(hikariConfig.getJdbcUrl());
        logger.info(hikariConfig.getUsername());
        logger.info(hikariConfig.getPassword());
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
