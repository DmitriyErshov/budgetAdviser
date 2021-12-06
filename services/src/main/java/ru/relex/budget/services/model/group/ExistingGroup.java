package ru.relex.budget.services.model.group;

import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.time.Instant;

@Value.Immutable
public interface ExistingGroup {

    long getId();

    String getGroupName();

    String getGroupDescription();

    Instant getCreatedAt();

    long getCreatedBy();

    String getJoinCode();

    BigDecimal getBalance();
}
