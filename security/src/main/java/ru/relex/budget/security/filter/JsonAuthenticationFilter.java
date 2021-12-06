package ru.relex.budget.security.filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class JsonAuthenticationFilter extends AuthenticationFilter {
  public JsonAuthenticationFilter(
    AuthenticationManager authenticationManager,
    AuthenticationConverter authenticationConverter
  ) {
    super(authenticationManager, authenticationConverter);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    return false;
  }

}
