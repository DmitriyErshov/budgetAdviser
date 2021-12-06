package ru.relex.budget.services.model.transaction;

import org.hibernate.validator.constraints.Length;
import org.immutables.value.Value;
import org.springframework.lang.NonNull;
import ru.relex.budget.services.validation.ValidationErrors;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Value.Immutable
public interface ExistingMoneyTransaction {

  long getId();

  @NotNull
  String getName();

  @NotNull
  long getCreatedBy();

  @NotNull
  long getGroupId();

  @NotNull
  double getAmount();

  @NotNull
  long getCategoryId();

  Instant getCreatedAt();
}
