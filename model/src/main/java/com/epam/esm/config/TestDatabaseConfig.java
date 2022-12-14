package com.epam.esm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Profile("dev")
@Configuration
@ComponentScan("com.epam.esm")
public class TestDatabaseConfig {
    private static final Logger logger = LogManager.getLogger();

    private static final String FILE_PATH = "/config/embedded-data-source.properties";
    private static final String INIT_DATA = "/script/embedded.sql";
    private static final String POPULATE_SQL = "/script/populate.sql";

    private static final String DATABASE_DRIVER_CLASS = "spring.datasource.driver-class-name";
    private static final String DATABASE_URL = "spring.datasource.url";
    private static final String DATABASE_USERNAME = "spring.datasource.username";
    private static final String DATABASE_PASSWORD = "spring.datasource.password";
    private static final String DATABASE_MAX_POOL_SIZE = "spring.datasource.max-pool-size";
    private static final String DATABASE_CONSOLE = "spring.h2.console.enabled";


    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder databaseBuilder = new EmbeddedDatabaseBuilder();
        return databaseBuilder.setType(EmbeddedDatabaseType.H2)
                .ignoreFailedDrops(true)
                .setScriptEncoding("UTF-8")
                .addScript(INIT_DATA)
                .addScript(POPULATE_SQL)
                .build();
    }
}
