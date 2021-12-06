package ru.relex.budget.db.model;

import lombok.Getter;
import lombok.Setter;
import ru.relex.budget.commons.model.Role;

@Getter
@Setter
public class UserProfile {

  private long id;
  private String username;
  private String email;
  private String phone;
  private String firstName;
  private String lastName;

  private Role role;

}
