package ru.relex.budget.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.relex.budget.db.model.UserModel;

import java.util.Collection;
import java.util.Set;

public class BudgetUserDetails implements UserDetails {

  private final String password;
  private final String username;
  private final boolean locked;
  private final boolean active;
  private final Set<SimpleGrantedAuthority> authorities;

  public BudgetUserDetails(UserModel model) {
    this.password = model.getPassword();
    this.username = model.getUsername();
    this.locked = model.isLocked();
    this.active = model.isActive();
    this.authorities = Set.of(new SimpleGrantedAuthority("ROLE_" + model.getRole().name()));
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !locked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return active;
  }
}
