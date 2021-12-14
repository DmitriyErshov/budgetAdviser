package ru.relex.budget.services.model.user;

import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Value.Immutable
@NotNull(message = "blah-blah")
public interface ExistingUser extends BaseUser {

  long getId();

  String getUsername();

  UserStatus getStatus();

  Instant getCreatedAt();

  @Nullable
  Long getCreatedBy();
}
