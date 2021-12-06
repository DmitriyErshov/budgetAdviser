package ru.relex.budget.services.internal.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.relex.budget.commons.model.Role;
import ru.relex.budget.db.mapper.GroupMapper;
import ru.relex.budget.db.mapper.UserMapper;
import ru.relex.budget.db.model.GroupModel;
import ru.relex.budget.db.model.UserModel;
import ru.relex.budget.services.internal.UserService;
import ru.relex.budget.services.mapper.GroupStruct;
import ru.relex.budget.services.mapper.UserStruct;
import ru.relex.budget.services.model.user.ExistingUser;
import ru.relex.budget.services.model.user.NewUser;
import ru.relex.budget.services.model.user.UpdatableUser;
import ru.relex.budget.services.security.PasswordEncryptor;

import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;

@Service
public class UserServiceImpl implements UserService {

  private final UserStruct userStruct;

  private final UserMapper userMapper;

  private final GroupMapper groupMapper;

  private final GroupStruct groupStruct;

  private final PasswordEncryptor passwordEncryptor;

  @Autowired
  public UserServiceImpl(final UserStruct userStruct,
                         final UserMapper userMapper,
                         final PasswordEncryptor passwordEncryptor,
                         final GroupMapper groupMapper,
                         final GroupStruct groupStruct) {
    this.userStruct = userStruct;
    this.userMapper = userMapper;
    this.passwordEncryptor = passwordEncryptor;
    this.groupMapper = groupMapper;
    this.groupStruct = groupStruct;
  }

  @Override
  public ExistingUser createUser(final NewUser user, long authorId) {

    final var newPassword = passwordEncryptor.encode(user.getPassword());

    //костыль, позволяющий понять, что пользователь пришел со страницы регистрации
    Role userRole = Role.USER;

    if(authorId == -1) {
      authorId = 1;
    } else if(user.getRole() != null) {
      userRole = user.getRole();
    }

    // Преобразовать NewUser в UserModel
    final var model = userStruct.fromNewUser(user, newPassword , authorId, userRole);

    //request to db
    UserModel newUser = userMapper.createUser(model);
    userMapper.setUserCreatedBy(newUser.getId(), authorId);


    if (newUser != null) {
      //создать группу для пользователя
      String joinCode = randomUUID().toString().substring(0, 8);
      final var groupModel = groupStruct.toGroupModel(model.getUsername(),
                                                "personal group", newUser.getId(), joinCode);

      //(insert) request to db
      GroupModel newGroup = groupMapper.createGroup(groupModel);

      UserModel model1 = userMapper.getNewId();
      System.out.println(model1.getId());
      groupMapper.saveGroupMember(model1.getId(), newGroup.getId(), Role.GROUP_ADMIN);
      //сгенерить activationCode
      joinCode = randomUUID().toString().substring(0, 8);
      userMapper.addActivationCode(newUser.getId(), joinCode);

      //FIXME отправка пользователю ссылки по email / phone для активации
      activateAccount(joinCode);
    }

    // Вернуть ExistingUser клиенту
    return userStruct.toExistingUser(model, newUser.getId(), newUser.getCreatedAt());
  }

  @Override
  public boolean activateAccount(String activationCode) {

    //найти по коду, получить id
    Long userId = userMapper.findByActivationCode(activationCode);

    //по id поставить флаг isActive
    userMapper.activateAccount(userId);
    userMapper.deleteActivationCode(userId);
    if(userId != -1)
      return true;

    return false;
  }

  @Override
  public boolean banUser(long userId) {
    return userMapper.banUserById(userId);
  }

  @Override
  public List<ExistingUser> getAllUsers() {
    List<UserModel> models = this.userMapper.findAll();
    List<ExistingUser> users = new ArrayList<>();
    for (var model : models) {
      users.add(this.userStruct.toExistingUser(model));
    }
    return users;
  }

  @Override
  public List<ExistingUser> getUsersWithLimitCount(long limit) {
    List<UserModel> models = this.userMapper.findUsersWithLimitCount(limit);
    List<ExistingUser> users = new ArrayList<>();
    for (var model : models) {
      users.add(this.userStruct.toExistingUser(model));
    }
    return users;
  }

  @Override
  public ExistingUser getById(long id) {
    return userStruct.toExistingUser(userMapper.findById(id));
  }

  @Override
  public ExistingUser update(long id, UpdatableUser updatableUser) {

    //get user from db and check for null
    ExistingUser user = userStruct.toExistingUser(userMapper.findById(id));
    if (user == null) {
      return null;
    }
    System.out.println(updatableUser.getPassword());
    final var newPassword = passwordEncryptor.encode(updatableUser.getPassword());
    System.out.println(newPassword);
    //get Status properties
    boolean isActive = updatableUser.getStatus().isActive();
    boolean isLocked = updatableUser.getStatus().isLocked();

    //UpdatableUser to UserModel
    final var model = userStruct.fromUpdatableUser(updatableUser, id, newPassword, isActive, isLocked);

    //update request to db
    userMapper.updateUser(model);

    //returns updated ExistingUser obj from db
    return getById(id);
  }

  @Override
  public boolean deleteById(long id) {
    userMapper.deleteUserById(id);
    return true;
  }

}
