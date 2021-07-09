package com.example.oracleaq.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class DataSourceConfig {

    @Value("${datasource.queue.jdbc-url}")
    private String JDBC_URL;

    @Value("${datasource.queue.username}")
    private String DB_USERNAME;

    @Value("${datasource.queue.password}")
    private String DB_PASSWORD;

    @Primary
    @Bean("simpleDriverDataSource")
    public DataSource dataSource() throws SQLException {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setUrl(JDBC_URL);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setDriverClass(oracle.jdbc.driver.OracleDriver.class);
        return dataSource;
    }

    @Bean("hikariDataSource")
    public DataSource hikariDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(JDBC_URL);
        hikariDataSource.setUsername(DB_USERNAME);
        hikariDataSource.setPassword(DB_PASSWORD);
        hikariDataSource.setDataSourceClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
        hikariDataSource.setMinimumIdle(5);
        hikariDataSource.setMaximumPoolSize(30);
        hikariDataSource.setConnectionTestQuery("/* ES (local) wini New Connection Pool Create */ SELECT 1 FROM DUAL");
        hikariDataSource.setConnectionTimeout(300000);
        return hikariDataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(@Qualifier("simpleDriverDataSource") DataSource dataSource) throws SQLException {
        return new DataSourceTransactionManager(dataSource);
    }





}
