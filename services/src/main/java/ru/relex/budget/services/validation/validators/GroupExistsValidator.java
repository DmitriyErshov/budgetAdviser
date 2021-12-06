package ru.relex.budget.services.validation.validators;

import org.springframework.beans.factory.annotation.Autowired;
import ru.relex.budget.db.mapper.GroupMapper;
import ru.relex.budget.services.validation.annotations.GroupExists;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class GroupExistsValidator implements ConstraintValidator<GroupExists, Long> {

  private final GroupMapper mapper;

  @Autowired
  public GroupExistsValidator(GroupMapper mapper) {
    this.mapper = mapper;
  }

   public boolean isValid(Long obj, ConstraintValidatorContext context) {
    if (obj == null) {
      return false;
    }
     return mapper.isGroupExists(obj);
   }
}
