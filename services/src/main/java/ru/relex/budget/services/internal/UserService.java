package ru.relex.budget.services.internal;

import ru.relex.budget.db.model.UserModel;
import ru.relex.budget.services.model.user.ExistingUser;
import ru.relex.budget.services.model.user.NewUser;
import ru.relex.budget.services.model.user.UpdatableUser;

import java.util.List;

public interface UserService {

  ExistingUser createUser(NewUser user, long authorId);

  boolean activateAccount(String activationCode);

  boolean banUser(long userId);

  List<ExistingUser> getAllUsers();

  List<ExistingUser> getUsersWithLimitCount(long limit);

  ExistingUser getById(long id);

  ExistingUser update(long id, UpdatableUser updatableUser);

  boolean deleteById(long id);
}
