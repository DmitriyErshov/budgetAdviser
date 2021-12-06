package ru.relex.budget.services.validation.annotations;

import ru.relex.budget.services.validation.validators.GroupExistsValidator;
import ru.relex.budget.services.validation.validators.TransactionExistsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Constraint(validatedBy = TransactionExistsValidator.class)
public @interface TransactionExists {
  String message();

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
