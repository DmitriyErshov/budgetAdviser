package ru.relex.budget.services.facade;

import javax.validation.Valid;

import org.hibernate.validator.constraints.Range;
import ru.relex.budget.services.model.user.ExistingUser;
import ru.relex.budget.services.model.user.NewUser;
import ru.relex.budget.services.model.user.UpdatableUser;
import ru.relex.budget.services.validation.annotations.ActivationCodeIsTrue;
import ru.relex.budget.services.validation.annotations.UserExists;

import java.util.List;

import static ru.relex.budget.services.validation.ValidationErrors.MONTH_NUM_IS_INVALID;

public interface UserFacade {

  ExistingUser createUser(@Valid NewUser user);

  boolean banUser(@UserExists(message = "user doesn't exist") long userId);

  List<ExistingUser> getAllUsers();

  List<ExistingUser> getUsersWithLimitCount(@Range(min = 1,
                                            message = "count must be positive") long countLimit);

  boolean activateAccount(@ActivationCodeIsTrue(message = "activationCode is invalid") String activationCode);

  ExistingUser getById(@UserExists(message = "user doesn't exist") long id);

  ExistingUser update(@UserExists(message = "user doesn't exist") long id, UpdatableUser updatableUser);

  ExistingUser getCurrentUser();

  boolean deleteById(@UserExists(message = "user doesn't exist") long id);

}
