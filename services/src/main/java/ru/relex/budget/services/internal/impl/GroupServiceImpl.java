package ru.relex.budget.services.internal.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.relex.budget.commons.model.Role;
import ru.relex.budget.db.mapper.GroupMapper;
import ru.relex.budget.db.model.GroupModel;
import ru.relex.budget.db.model.UserModel;
import ru.relex.budget.services.internal.GroupService;
import ru.relex.budget.services.mapper.GroupStruct;
import ru.relex.budget.services.mapper.UserStruct;
import ru.relex.budget.services.model.group.ExistingGroup;
import ru.relex.budget.services.model.group.NewGroup;
import ru.relex.budget.services.model.group.UpdatableGroup;
import ru.relex.budget.services.model.user.ExistingUser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;


@Service
public class GroupServiceImpl implements GroupService {

  private final GroupStruct groupStruct;

  private final UserStruct userStruct;

  private final GroupMapper groupMapper;

  @Autowired
  public GroupServiceImpl(final GroupStruct groupStruct,
                          final GroupMapper groupMapper,
                          final UserStruct userStruct) {
    this.groupMapper = groupMapper;
    this.groupStruct = groupStruct;
    this.userStruct = userStruct;
  }

  @Override
  public ExistingGroup createGroup(final NewGroup group, long authorId) {

    // Преобразовать NewGroup в GroupModel
    //код для приглашения
    String joinCode = randomUUID().toString().substring(0, 36);
    final var model = groupStruct.fromNewGroup(group, authorId, joinCode);

    //(insert) request to db
    GroupModel newGroup = groupMapper.createGroup(model);

    groupMapper.saveGroupMember(model.getCreatedBy(), newGroup.getId(), Role.GROUP_ADMIN);

    return groupStruct.toExistingGroup(model, newGroup.getId(), newGroup.getCreatedAt());
  }

  @Override
  public ExistingGroup getById(long id) {
    return groupStruct.toExistingGroup(groupMapper.findById(id));
  }

  @Override
  public List<ExistingUser> getGroupMembers(long id) {
    List<UserModel> models = groupMapper.findGroupMembersById(id);

    List<ExistingUser> members = new ArrayList<>();
    for (UserModel model : models) {
      members.add(userStruct.toExistingUser(model, model.getId(), model.getCreatedAt()));
    }

    return members;
  }

  @Override
  public ExistingGroup update(UpdatableGroup updatableGroup) {

    //get group from db and check for null
    int groupId = groupMapper.findByGroupAdmin(updatableGroup.getGroupAdmin());
      ExistingGroup group = groupStruct.toExistingGroup(groupMapper.findById(groupId));
    if (group == null) {
      return null;
    }

    //UpdatableGroup to GroupModel
    final var model = groupStruct.fromUpdatableGroup(updatableGroup,groupId);

    //update request to db
    groupMapper.updateGroup(model);

    //returns updated ExistingGroup obj from db
    return getById(groupId);
  }

  @Override
  public boolean addGroupMember(long groupId, long userId, Role role) {
    groupMapper.saveGroupMember(userId, groupId, role);
    //TODO сделать что-нибудь с этим
    return true;
  }

  @Override
  public boolean deleteGroupMember(long groupId, long userId) {
    groupMapper.deleteGroupMember(userId, groupId);
    return true;
  }

  @Override
  public boolean addGroupMemberWithJoinCode(String joinCode, long userId) {

    GroupModel groupModel = groupMapper.findByJoinCode(joinCode);

    groupMapper.saveGroupMember(userId, groupModel.getId(), Role.GROUP_USER);

    return true;
  }



  @Override
  public boolean deleteById(long groupId) {
    groupMapper.deleteGroupById(groupId);
    return true;
  }

  @Override
  public List<ExistingGroup> getAllGroups() {
    List<GroupModel> models = this.groupMapper.findAll();
    List<ExistingGroup> groups = new ArrayList<>();
    for (var model : models) {
      groups.add(this.groupStruct.toExistingGroup(model));
    }
    return groups;
  }

  @Override
  public List<ExistingGroup> getGroupsBuUserId(long userId) {
    List<GroupModel> models = this.groupMapper.findGroupsByUserId(userId);
    List<ExistingGroup> groups = new ArrayList<>();
    for (var model : models) {
      groups.add(this.groupStruct.toExistingGroup(model));
    }
    return groups;
  }
}
