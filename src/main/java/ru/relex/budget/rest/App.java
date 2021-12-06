package ru.relex.budget.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.relex.budget.services.ServicesConfiguration;

@SpringBootApplication
@Import({
  ServicesConfiguration.class,
  ru.relex.budget.security.SecurityConfig.class
})
public class App {
  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }
}
