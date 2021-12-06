package ru.relex.budget.db.model;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.relex.budget.commons.model.Role;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {
  "firstName",
  "lastName",
  "password",
  "createdAt",
  "createdBy",
  "active",
  "locked",
  "email",
  "phone"
})
public class UserModel {

  private long id;

  private String firstName;
  private String lastName;

  private String email;
  private String phone;

  private boolean active;
  private boolean locked;

  private String username;
  private String password;

  private Long createdBy;
  private Instant createdAt;

  private Role role;

}
