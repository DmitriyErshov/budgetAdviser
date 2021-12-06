package ru.relex.budget.services.security;

public interface PasswordEncryptor {

  String encode(final String password);
}
