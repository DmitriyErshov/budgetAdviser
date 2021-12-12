package ru.relex.budget.services.model.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@Value.Immutable
//@Value.Style(
//  typeImmutable = "Immutable", // No prefix or suffix for generated immutable type
//  //visibility = Value.Style.ImplementationVisibility.PRIVATE, // Generated class will be always public
//  builderVisibility = Value.Style.BuilderVisibility.PUBLIC,
//  defaults = @Value.Immutable(builder = true))
//@Value.Include({ ImmutableUpdatableUser.class })
@JsonDeserialize(builder = ImmutableUpdatableUser.Builder.class)
@Value.Enclosing
public interface UpdatableUser extends BaseUser {

  String getPassword();

  UserStatus getStatus();
}
