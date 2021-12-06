package ru.relex.budget.security.encoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.relex.budget.services.security.PasswordEncryptor;

@Component
public class BudgetPasswordEncryptor implements PasswordEncryptor {

  private final PasswordEncoder passwordEncoder;

  public BudgetPasswordEncryptor(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public String encode(String password) {
    return passwordEncoder.encode(password);
  }
}
