package ru.relex.budget.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.relex.budget.services.ServicesConfiguration;
import ru.relex.budget.security.SecurityConfig;

@SpringBootApplication(proxyBeanMethods = false)
@Import({
  ServicesConfiguration.class,
  SecurityConfig.class
})
public class App {
  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }
}
