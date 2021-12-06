package ru.relex.budget.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import ru.relex.budget.db.mapper.UserAuthMapper;
import ru.relex.budget.db.model.UserProfile;
import ru.relex.budget.security.model.BudgetUserDetails;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class BudgetAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  private static final Logger logger = LoggerFactory.getLogger(BudgetAuthenticationSuccessHandler.class);

  private final UserAuthMapper userMapper;
  protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public BudgetAuthenticationSuccessHandler(UserAuthMapper userMapper) {
    this.userMapper = userMapper;
  }

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authentication
  ) throws IOException, ServletException {

    onAuthenticationSuccess(request,response,authentication);

  }

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication
  ) throws IOException, ServletException {

    response.setStatus(HttpServletResponse.SC_OK);

    var object = authentication.getPrincipal();

    if( !(object instanceof BudgetUserDetails)){
      logger.error("Expected UserDetails but recieved {}", object.getClass());
      throw new ServletException("Unexpected authorization class");
    }

    UserProfile profile = userMapper.getProfileInfo(((BudgetUserDetails) object).getUsername());

    SecurityContextHolder.getContext()
      .setAuthentication(new UsernamePasswordAuthenticationToken(
        profile,
        null,
        Set.of(new SimpleGrantedAuthority("ROLE_" + profile.getRole().name()))
      )
    );

    OBJECT_MAPPER.writeValue(response.getWriter(),profile);
  }
}
