package ru.relex.budget.db;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan
@MapperScan(basePackages = "ru.relex.budget.db.mapper")
@EnableTransactionManagement
public class DatabaseConfig {

}
