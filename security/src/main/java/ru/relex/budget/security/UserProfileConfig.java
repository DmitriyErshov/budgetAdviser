package ru.relex.budget.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import ru.relex.budget.db.model.UserProfile;

@Configuration
public class UserProfileConfig {

  @Bean
  @Scope(
    proxyMode = ScopedProxyMode.TARGET_CLASS,
    scopeName = WebApplicationContext.SCOPE_REQUEST
  )
  UserProfile userProfile(){
    var profileObj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if(profileObj instanceof UserProfile){
      return (UserProfile) profileObj;
    }

    //var anonymous = profileObj.toString();
    var profile = new UserProfile();
    profile.setId(-1);

    return profile;
  }
}
