package ru.relex.budget.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.relex.budget.commons.model.Role;
import ru.relex.budget.db.mapper.UserAuthMapper;
import ru.relex.budget.security.converter.BudgetAuthenticationConverter;
import ru.relex.budget.security.filter.JsonAuthenticationFilter;
import ru.relex.budget.security.handler.BudgetAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity (debug = true)
@EnableGlobalMethodSecurity(
  jsr250Enabled = true,
  prePostEnabled = true,
  securedEnabled = true
)
@ComponentScan(basePackageClasses = SecurityConfig.class)
//@EnableAutoConfiguration
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private final UserDetailsService userDetailsService;
    private final UserAuthMapper userMapper;

  @Autowired
    public SecurityConfig
    (
      final UserDetailsService userDetailsService,
      final UserAuthMapper userMapper
    ) {
      this.userDetailsService = userDetailsService;
      this.userMapper = userMapper;
    }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new Pbkdf2PasswordEncoder("", 178_123, 512);
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    super.configure(web);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder builder) {
    final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(passwordEncoder());
    provider.setUserDetailsService(userDetailsService);
    builder.authenticationProvider(provider);
  }

  @Bean
  @Override
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

      final var authenticationFilter = new JsonAuthenticationFilter(authenticationManager(), new BudgetAuthenticationConverter());
      authenticationFilter.setRequestMatcher(
        new AntPathRequestMatcher("/auth/login", "POST")
      );

      authenticationFilter.setSuccessHandler(new BudgetAuthenticationSuccessHandler(userMapper));

      //вынести в отдельный класс
      authenticationFilter.setFailureHandler(new AuthenticationFailureHandler() {

      private final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
          logger.error("Error", exception);
          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
      });

    http
      .cors().disable()
      .csrf()/*.csrfTokenRepository(csrfTokenrepository()).and()*/.disable()
      .authorizeRequests()
      .antMatchers("/public/**").permitAll()
      .antMatchers("/auth/**").not().authenticated()
      .antMatchers("/admin/**").hasAnyAuthority(Role.ADMIN.name())
      .anyRequest().authenticated()
      .and()
      .logout()
      .permitAll()
      //.logoutSuccessUrl("/public/homePage")
      .and()
      .addFilterAfter(authenticationFilter, LogoutFilter.class);
    }

    //TODO затереть в проде
  public static void main(String[] args) {
    final var encoder  = new Pbkdf2PasswordEncoder("", 178_123, 512);
    String encoded = encoder.encode("test");
    System.out.println(encoded);
  }


//  @Bean
//  CorsConfigurationSource corsConfigurationSource() {
//    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//    var cc = new CorsConfiguration();
//    cc.addAllowedOrigin("http://localhost:4200");
//    source.registerCorsConfiguration("/**", cc);
//    return source;
//  }


  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
      .allowedOrigins("http://localhost:4200")
      .allowedMethods("*");
  }
//
//  private CsrfTokenRepository csrfTokenRepository() {
//    var csrfRepo = CookieCsrfTokenRepository.withHttpOnlyFalse();
//    csrfRepo.setCookiePath("/");
//    return csrfRepo;
//  }

}
