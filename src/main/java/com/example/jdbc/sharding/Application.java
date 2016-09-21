package com.example.jdbc.sharding;

import com.dangdang.ddframe.rdb.sharding.jdbc.ShardingDataSource;
import com.dangdang.ddframe.rdb.sharding.spring.datasource.SpringShardingDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;

/**
 * bootstrap class
 * Created by fangtao on 16/8/20.
 */
@SpringBootApplication
public class Application {
    @Resource
    DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }
}
