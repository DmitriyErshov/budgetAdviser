package ru.relex.budget.services.model.group;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.hibernate.validator.constraints.Length;
import org.immutables.value.Value;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.relex.budget.services.validation.ValidationErrors;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Value.Immutable
@JsonDeserialize(builder = ImmutableNewGroup.Builder.class)
public interface NewGroup {

    @NonNull
    @Length(
       min = 4,
       max = 50,
       message = ValidationErrors.GROUP_NAME_MUST_BE_SET
    )
    String getGroupName();

    @NonNull
    @Nullable
    @Length(
        min = 5,
        max = 150
    )
    String getDescription();

    //сделать проверку на существование
}
