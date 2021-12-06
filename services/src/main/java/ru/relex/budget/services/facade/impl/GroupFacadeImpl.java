package ru.relex.budget.services.facade.impl;

import org.springframework.transaction.annotation.Transactional;
import ru.relex.budget.commons.model.Role;
import ru.relex.budget.db.model.UserProfile;
import ru.relex.budget.services.facade.GroupFacade;
import ru.relex.budget.services.internal.GroupService;
import ru.relex.budget.services.meta.Facade;
import ru.relex.budget.services.model.group.ExistingGroup;
import ru.relex.budget.services.model.group.NewGroup;
import ru.relex.budget.services.model.group.UpdatableGroup;
import ru.relex.budget.services.model.user.ExistingUser;
import ru.relex.budget.services.validation.annotations.GroupExists;
import ru.relex.budget.services.validation.annotations.JoinCodeIsTrue;
import ru.relex.budget.services.validation.annotations.UserExists;

import javax.validation.Valid;
import java.util.List;

@Facade
public class GroupFacadeImpl implements GroupFacade {

  private final GroupService groupService;
  private final UserProfile currentUser;

  public GroupFacadeImpl(final GroupService groupService,
                         final UserProfile currentUser) {
    this.groupService = groupService;
    this.currentUser = currentUser;
  }

  @Override
  public ExistingGroup createGroup(@Valid NewGroup group) {
    return groupService.createGroup(group, currentUser.getId());
  }

  @Override
  public ExistingGroup getById(@GroupExists(message = "group does't exist") long id) {
    return groupService.getById(id);
  }

  @Override
  public List<ExistingUser> getGroupMembers(long id) {
    return groupService.getGroupMembers(id);
  }

  @Override
  public ExistingGroup update(UpdatableGroup group) {
    return groupService.update(group);
  }

  @Override
  @Transactional
  public boolean addGroupMember(@GroupExists(message = "group does't exist") long groupId,
                                @UserExists(message = "user doesn't exist") long userId,
                                Role role) {
    return groupService.addGroupMember(groupId, userId, role);
  }

  @Override
  public boolean deleteGroupMember(long groupId, long userId) {
    return groupService.deleteGroupMember(groupId, userId);
  }

  @Override
  public boolean addGroupMemberWithJoinCode(@JoinCodeIsTrue(message = "joinCode is invalid") String joinCode) {
    return groupService.addGroupMemberWithJoinCode(joinCode, currentUser.getId());
  }

  @Override
  public boolean deleteById(long groupId) {
    return groupService.deleteById(groupId);
  }

  @Override
  public List<ExistingGroup> getAllGroups() {
    return groupService.getAllGroups();
  }

  @Override
  public List<ExistingGroup> getGroupsByUser(long userId) {
    return groupService.getGroupsBuUserId(userId);
  }
}
