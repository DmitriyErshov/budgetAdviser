package ru.relex.budget.services.model.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;
import ru.relex.budget.services.validation.ValidationErrors;

import javax.validation.constraints.NotNull;
import java.time.Instant;



@Value.Immutable
@JsonDeserialize(builder = ImmutableNewUser.Builder.class)
@Value.Style(redactedMask = "*****")
public interface NewUser extends BaseUser {

    //@NotNull(message = ValidationErrors.USERNAME_MUST_BE_SET)
    String getUsername();

    @Value.Redacted
    String getPassword();

}
