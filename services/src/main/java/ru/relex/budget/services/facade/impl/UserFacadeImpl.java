package ru.relex.budget.services.facade.impl;

import javax.validation.Valid;

import org.hibernate.validator.constraints.Range;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.relex.budget.db.model.UserProfile;
import ru.relex.budget.services.facade.UserFacade;
import ru.relex.budget.services.internal.UserService;
import ru.relex.budget.services.model.user.ExistingUser;
import ru.relex.budget.services.model.user.NewUser;
import ru.relex.budget.services.model.user.UpdatableUser;
import ru.relex.budget.services.meta.Facade;
import ru.relex.budget.services.validation.annotations.ActivationCodeIsTrue;
import ru.relex.budget.services.validation.annotations.UserExists;

import java.util.List;

@Facade
public class UserFacadeImpl implements UserFacade {

  private final UserService userService;
  private final UserProfile currentUser;

  public UserFacadeImpl(
      final UserService userService,
      final UserProfile currentUser) {
    this.userService = userService;
    this.currentUser = currentUser;
  }

  @Override
  @Transactional
  public ExistingUser createUser(@Valid final NewUser user) {
    return userService.createUser(user,currentUser.getId());
  }

  @Override
  public boolean banUser(@UserExists(message = "user doesn't exist") long userId) {
    return userService.banUser(userId);
  }

  @Override
  public List<ExistingUser> getAllUsers() {
    return userService.getAllUsers();
  }

  @Override
  public List<ExistingUser> getUsersWithLimitCount(@Range(min = 1,
    message = "count must be positive") long countLimit) {
    return null;
  }

  @Override
  public boolean activateAccount(@ActivationCodeIsTrue(message = "activationCode is invalid") String activationCode) {
    return userService.activateAccount(activationCode);
  }

  @Override
  public ExistingUser getById(@UserExists(message = "user doesn't exist") long id) {
    return userService.getById(id);
  }

  @Override
  public ExistingUser update(@UserExists(message = "user doesn't exist") long id, UpdatableUser updatableUser) {
    return userService.update(id, updatableUser);
  }

  @Override
  public ExistingUser getCurrentUser() {
    return userService.getById(currentUser.getId());
  }

  @Override
  public boolean deleteById(@UserExists(message = "user doesn't exist") long id) {
    return userService.deleteById(id);
  }

}
