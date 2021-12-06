package ru.relex.budget.services.model.transaction;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import javax.validation.constraints.NotNull;

@Value.Immutable
@JsonDeserialize(builder = ImmutableUpdatableMoneyTransaction.Builder.class)
public interface UpdatableMoneyTransaction {

  String getName();

  String getSum();

  long getCategoryId();
}
