package ru.relex.budget.services.validation.validators;

import org.springframework.beans.factory.annotation.Autowired;
import ru.relex.budget.db.mapper.UserMapper;
import ru.relex.budget.services.validation.annotations.ActivationCodeIsTrue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ActivaitionCodeValidator implements ConstraintValidator<ActivationCodeIsTrue, String> {

  private final UserMapper userMapper;

  @Autowired
  ActivaitionCodeValidator(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  public boolean isValid(String obj, ConstraintValidatorContext context) {
    return userMapper.findByActivationCode(obj) != null;
  }
}
