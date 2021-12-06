package ru.relex.budget.services.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.relex.budget.services.model.group.ExistingGroup;
import ru.relex.budget.services.model.group.NewGroup;
import ru.relex.budget.services.model.group.UpdatableGroup;
import ru.relex.budget.db.model.GroupModel;

import java.math.BigDecimal;
import java.time.Instant;

@Mapper
public interface GroupStruct {

  @Mapping(target = "groupName", source = "groupName")
  @Mapping(target = "description", source = "groupDescription")
  @Mapping(target = "createdBy", source = "createdBy")
  @Mapping(target = "joinCode", source = "joinCode")
  GroupModel toGroupModel(String groupName, String groupDescription, long createdBy, String joinCode);

  @Mapping(target = "groupName", source = "group.groupName")
  @Mapping(target = "description", source = "group.description")
  @Mapping(target = "createdBy", source = "authorId")
  @Mapping(target = "joinCode", source = "joinCode")
  GroupModel fromNewGroup(NewGroup group, long authorId, String joinCode);

  @Mapping(target = "groupName", source = "model.groupName")
  @Mapping(target = "groupDescription", source = "model.description")
  @Mapping(target = "id", source = "id")
  @Mapping(target = "createdAt", source = "createdAt")
  @Mapping(target = "balance", source = "model.balance")
  ExistingGroup toExistingGroup(GroupModel model, long id, Instant createdAt);

  @Mapping(target = "groupName", source = "byId.groupName")
  @Mapping(target = "groupDescription", source = "byId.description")
  @Mapping(target = "createdAt", source = "byId.createdAt")
  @Mapping(target = "balance", source = "byId.balance")
  ExistingGroup toExistingGroup(GroupModel byId);

  @Mapping(target = "groupName", source = "updatableGroup.groupName")
  @Mapping(target = "description", source = "updatableGroup.groupDescription")
  @Mapping(target = "id",source = "groupId")
  GroupModel fromUpdatableGroup(UpdatableGroup updatableGroup, long groupId);
}
