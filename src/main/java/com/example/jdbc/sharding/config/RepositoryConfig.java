package com.example.jdbc.sharding.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by fangtao on 16/8/21.
 */
@Configuration
public class RepositoryConfig {
    @Bean
    public DataSource dataSource() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-repository.xml");
        DataSource dataSource = applicationContext.getBean("dataSource", DataSource.class);
        applicationContext.close();
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
