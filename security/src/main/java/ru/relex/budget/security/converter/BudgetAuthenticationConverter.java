package ru.relex.budget.security.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import ru.relex.budget.security.model.UsernamePasswordRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class BudgetAuthenticationConverter implements AuthenticationConverter {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final Logger logger = LoggerFactory.getLogger(BudgetAuthenticationConverter.class);

  @Override
  public Authentication convert(HttpServletRequest request) {

    UsernamePasswordRequest usernamePasswordRequest;
    try (var inputStream = request.getInputStream()){
      usernamePasswordRequest = OBJECT_MAPPER.readValue(inputStream, UsernamePasswordRequest.class);
    } catch (IOException e) {
      logger.error("Cannot read login request",e);
      return null;
    }

    return new UsernamePasswordAuthenticationToken(
        usernamePasswordRequest.getUsername(),
        usernamePasswordRequest.getPassword()
    );
  }
}
