package ru.relex.budget.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.relex.budget.db.mapper.UserAuthMapper;
import ru.relex.budget.security.model.BudgetUserDetails;

@Service
public class BudgetUserDetailsService implements UserDetailsService {

  private final UserAuthMapper userMapper;

  public BudgetUserDetailsService(UserAuthMapper userMapper){
    this.userMapper = userMapper;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userMapper.getUserByUsernameOrEmailOrPhone(username)
      .map(BudgetUserDetails::new)
      .orElseThrow(() -> new UsernameNotFoundException(
        "User with name [" + username + "] not found")
      );
  }
}
