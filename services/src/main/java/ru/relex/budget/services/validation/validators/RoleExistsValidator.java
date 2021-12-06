package ru.relex.budget.services.validation.validators;

import ru.relex.budget.commons.model.Role;
import ru.relex.budget.services.validation.annotations.RoleExists;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RoleExistsValidator implements ConstraintValidator<RoleExists, Role> {

  public boolean isValid(Role obj, ConstraintValidatorContext context) {
    return obj != null;
  }

}
