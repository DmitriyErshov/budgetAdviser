package ru.relex.budget.services.model.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
//import org.immutables.value.Value;

@Value.Immutable
@Value.Style(defaultAsDefault = true)
@JsonDeserialize(builder = ImmutableUserStatus.Builder.class)
public interface UserStatus {
    default boolean isActive(){
        return false;
    }
    default boolean isLocked(){
        return false;
    }
}
