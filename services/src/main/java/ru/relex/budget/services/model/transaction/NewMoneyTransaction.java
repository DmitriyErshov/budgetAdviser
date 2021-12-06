package ru.relex.budget.services.model.transaction;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.immutables.value.Value;

import javax.validation.constraints.NotNull;

import static ru.relex.budget.services.validation.ValidationErrors.CATEGORY_ID_IS_INVALID;
import static ru.relex.budget.services.validation.ValidationErrors.TRANSACTION_NAME_MUST_BE_SET;

@Value.Immutable
@JsonDeserialize(builder = ImmutableNewMoneyTransaction.Builder.class)
public interface NewMoneyTransaction {

  @NotNull
  @Length(
    min = 4,
    max = 30,
    message = TRANSACTION_NAME_MUST_BE_SET
  )
  String getName();

  @NotNull
  double getAmount();

  @NotNull
  long getGroupId();


  //TODO вынести константу maxCategoryNum куда нибудь в файл
  @NotNull
  @Range(min = 1,
         max = 12,
         message = CATEGORY_ID_IS_INVALID)
  long getCategoryId();
}
