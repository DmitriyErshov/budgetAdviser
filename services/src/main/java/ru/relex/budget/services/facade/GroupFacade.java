package ru.relex.budget.services.facade;

import ru.relex.budget.commons.model.Role;
import ru.relex.budget.services.model.group.ExistingGroup;
import ru.relex.budget.services.model.group.NewGroup;
import ru.relex.budget.services.model.group.UpdatableGroup;
import ru.relex.budget.services.model.user.ExistingUser;
import ru.relex.budget.services.validation.annotations.GroupExists;
import ru.relex.budget.services.validation.annotations.JoinCodeIsTrue;
import ru.relex.budget.services.validation.annotations.UserExists;

import javax.validation.Valid;
import java.util.List;

public interface GroupFacade {

    ExistingGroup createGroup(@Valid NewGroup group);

    ExistingGroup getById(@GroupExists(message = "group does't exist") long id);

    List<ExistingUser> getGroupMembers(@GroupExists(message = "group does't exist")long id);

    ExistingGroup update(UpdatableGroup group);

  boolean addGroupMember(@GroupExists(message = "group does't exist") long groupId,
                           @UserExists(message = "user doesn't exist") long userId,
                           Role role);

    boolean deleteGroupMember(@GroupExists(message = "group does't exist") long groupId,
                              @UserExists(message = "user doesn't exist") long userId);

    boolean addGroupMemberWithJoinCode(@JoinCodeIsTrue(message = "joinCode is invalid") String joinCode);

    boolean deleteById(@GroupExists(message = "group does't exist") long groupId);

    List<ExistingGroup> getAllGroups();

    List<ExistingGroup> getGroupsByUser(@UserExists(message = "user doesn't exist") long userId);
}
