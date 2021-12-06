package ru.relex.budget.services.validation.validators;

import org.springframework.beans.factory.annotation.Autowired;
import ru.relex.budget.db.mapper.GroupMapper;
import ru.relex.budget.services.validation.annotations.GroupExists;
import ru.relex.budget.services.validation.annotations.JoinCodeIsTrue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class JoinCodeValidator implements ConstraintValidator<JoinCodeIsTrue, String> {

  private final GroupMapper groupMapper;

  @Autowired
  JoinCodeValidator(GroupMapper groupMapper) {
    this.groupMapper = groupMapper;
  }

  public boolean isValid(String obj, ConstraintValidatorContext context) {
    return groupMapper.findByJoinCode(obj) != null;
  }
}
