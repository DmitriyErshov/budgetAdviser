package ru.relex.budget.services.model.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.immutables.value.Value;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.relex.budget.services.validation.ValidationErrors;



@JsonDeserialize(builder = ImmutablePersonalInfo.Builder.class)
@Value.Immutable
@NotNull(message = "blah-blah")
public interface PersonalInfo {

  @Nullable
  @Length(
    min = 1,
    max = 50,
    message = ValidationErrors.FIRST_NAME_LENGTH_IS_INVALID
  )
  String getFirstName();

  @Nullable
  @Length(
    min = 1,
    max = 50,
    message = ValidationErrors.LAST_NAME_LENGTH_IS_INVALID
  )
  String getLastName();

  @NonNull
  @Value.Derived
  default String getFullName() {
    if (
      (getFirstName() == null || getFirstName().isBlank())
        && (getLastName() == null || getLastName().trim().isEmpty())
    ) {
      return "";
    }

    if (getFirstName() == null) {
      return getLastName().strip();
    }

    if (getLastName() == null) {
      return getFirstName().strip();
    }

    return (getFirstName() + " " + getLastName()).strip();

  }

  @Pattern(regexp = "[a-zA-Z\\d-_.]+@[a-zA-Z\\d-_.]{3,}", message = ValidationErrors.EMAIL_HAS_INVALID_FORMAT)
  String getEmail();

  @Pattern(regexp = "\\d{5,15}",message = ValidationErrors.PHONE_FORMAT_IS_INVALID)
  String getPhone();
}
