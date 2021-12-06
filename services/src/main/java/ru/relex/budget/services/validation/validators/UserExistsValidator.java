package ru.relex.budget.services.validation.validators;

import org.springframework.beans.factory.annotation.Autowired;
import ru.relex.budget.db.mapper.UserMapper;
import ru.relex.budget.services.validation.annotations.UserExists;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserExistsValidator implements ConstraintValidator<UserExists, Long> {

  private final UserMapper userMapper;

  @Autowired
  UserExistsValidator(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  public boolean isValid(Long obj, ConstraintValidatorContext context) {
    if (obj == null) {
      return false;
    }
    return userMapper.isUserExists(obj);
  }
}

