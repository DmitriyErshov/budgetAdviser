package ru.relex.budget.services.model.user;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;
import ru.relex.budget.services.validation.ValidationErrors;
import ru.relex.budget.commons.model.Role;
import ru.relex.budget.services.validation.annotations.RoleExists;


public interface BaseUser {

  @Nullable
  //@NotNull(message = ValidationErrors.ROLE_MUST_BE_SET)
  Role getRole();


  @Valid
  @NotNull(message = ValidationErrors.PERSONAL_INFO_MUST_BE_SET)
  PersonalInfo getPersonalInfo();

}
