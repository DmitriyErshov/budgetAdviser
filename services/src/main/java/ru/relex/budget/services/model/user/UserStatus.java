package ru.relex.budget.services.model.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.elasticsearch.common.Nullable;
import org.immutables.value.Value;

import javax.validation.constraints.NotNull;
//import org.immutables.value.Value;


@Value.Immutable
@Value.Style(defaultAsDefault = true)
@JsonDeserialize(builder = ImmutableUserStatus.Builder.class)

@NotNull(message = "blah-blah")
//@Nullable
public interface UserStatus {
    default boolean isActive(){
        return false;
    }
    default boolean isLocked(){
        return false;
    }
}
