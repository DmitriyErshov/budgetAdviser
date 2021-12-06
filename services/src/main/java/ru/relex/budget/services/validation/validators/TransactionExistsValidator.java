package ru.relex.budget.services.validation.validators;

import org.springframework.beans.factory.annotation.Autowired;
import ru.relex.budget.db.mapper.MoneyTransactionMapper;
import ru.relex.budget.services.validation.annotations.GroupExists;
import ru.relex.budget.services.validation.annotations.TransactionExists;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TransactionExistsValidator implements ConstraintValidator<TransactionExists, Long> {

  private final MoneyTransactionMapper transactionMapper;

  @Autowired
  TransactionExistsValidator(MoneyTransactionMapper transactionMapper) {
    this.transactionMapper = transactionMapper;
  }

  public boolean isValid(Long obj, ConstraintValidatorContext context) {
    if (obj == null) {
      return false;
    }
    return transactionMapper.isTransactionExists(obj);
  }
}
