package ru.relex.budget.services;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.relex.budget.db.*;

@Configuration
@Import(DatabaseConfig.class)
@ComponentScan(basePackageClasses = ServicesConfiguration.class)
public class ServicesConfiguration {
}
