package ru.relex.budget.services.model.group;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(builder = ImmutableUpdatableGroup.Builder.class)
public interface UpdatableGroup {

    String getGroupName();

    String getGroupDescription();

    int getGroupAdmin();
}
