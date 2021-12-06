package ru.relex.budget.services.model.user;

import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import java.time.Instant;

@Value.Immutable
public interface ExistingUser extends BaseUser {

  long getId();

  String getUsername();

  UserStatus getStatus();

  Instant getCreatedAt();

  @Nullable
  Long getCreatedBy();
}
