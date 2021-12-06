package ru.relex.budget.services.internal;

import ru.relex.budget.commons.model.Role;
import ru.relex.budget.services.model.group.ExistingGroup;
import ru.relex.budget.services.model.group.NewGroup;
import ru.relex.budget.services.model.group.UpdatableGroup;
import ru.relex.budget.services.model.user.ExistingUser;

import java.util.List;

public interface GroupService {

  ExistingGroup createGroup(NewGroup group, long authorId);

  ExistingGroup getById(long id);

  List<ExistingUser> getGroupMembers(long id);

  ExistingGroup update(UpdatableGroup group);

  boolean addGroupMember(long groupId, long userId, Role role);

  boolean deleteGroupMember(long groupId, long userId);

  boolean addGroupMemberWithJoinCode(String joinCode, long userId);

  boolean deleteById(long groupId);

  List<ExistingGroup> getAllGroups();

  List<ExistingGroup> getGroupsBuUserId(long userId);
}
