package ru.relex.budget.services.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Qualifier;
import ru.relex.budget.commons.model.Role;
import ru.relex.budget.db.model.UserModel;
import ru.relex.budget.services.model.user.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.Instant;

@Mapper
public interface UserStruct {

  @Mapping(target = "status", source = "updateData.status")
  @Mapping(target = "personalInfo", source = "updateData.personalInfo")
  @Mapping(target = "role", source = "updateData.role")
  ExistingUser merge(ExistingUser user, UpdatableUser updateData);

  @Mapping(target = "firstName", source = "user.personalInfo.firstName")
  @Mapping(target = "lastName", source = "user.personalInfo.lastName")
  @Mapping(target = "email", source = "user.personalInfo.email")
  @Mapping(target = "phone", source = "user.personalInfo.phone")
  @Mapping(target = "active", constant = "false")
  @Mapping(target = "locked", constant = "false")
  @Mapping(target = "password", source = "password")
  @Mapping(target = "role", source = "role")
  UserModel fromNewUser(NewUser user, String password, Long createdBy, Role role);

  @Mapping(target = "personalInfo.firstName", source = "model.firstName")
  @Mapping(target = "personalInfo.lastName", source = "model.lastName")
  @Mapping(target = "personalInfo.email", source = "model.email")
  @Mapping(target = "personalInfo.phone", source = "model.phone")
  @Mapping(target = "id", source = "id")
  @Mapping(target = "createdAt", source = "createdAt")
  @Mapping(target = "status.isActive", source = "model.active")
  @Mapping(target = "status.isLocked", source = "model.locked")
  ExistingUser toExistingUser(UserModel model, long id, Instant createdAt);

  @Mapping(target = "personalInfo.firstName", source = "byId.firstName")
  @Mapping(target = "personalInfo.lastName", source = "byId.lastName")
  @Mapping(target = "personalInfo.email", source = "byId.email")
  @Mapping(target = "personalInfo.phone", source = "byId.phone")
  @Mapping(target = "status.isActive", source = "byId.active")
  @Mapping(target = "status.isLocked", source = "byId.locked")
  ExistingUser toExistingUser(UserModel byId);

  @Mapping(target = "firstName", source = "updatableUser.personalInfo.firstName")
  @Mapping(target = "lastName", source = "updatableUser.personalInfo.lastName")
  @Mapping(target = "email", source = "updatableUser.personalInfo.email")
  @Mapping(target = "phone", source = "updatableUser.personalInfo.phone")
  @Mapping(target = "password", source = "NewPassword")
  @Mapping(target = "active", source = "isActive")
  @Mapping(target = "locked", source = "isLocked")
  @Mapping(target = "role", source = "updatableUser.role")
  @Mapping(target = "id", source = "id")
  UserModel fromUpdatableUser(UpdatableUser updatableUser,long id,String NewPassword,boolean isActive,boolean isLocked);

  @Qualifier
  @Retention(RetentionPolicy.CLASS)
  @interface DefaultStatusMapper {
  }

  /*@Mapping(target = "id", source = "newId")
  @Mapping(target = "status", source = "user", qualifiedBy = DefaultStatusMapper.class)
  ExistingUser fromNewUser(NewUser user, long newId);*/

  @DefaultStatusMapper
  default UserStatus defaultStatusMapper(@SuppressWarnings("unused") NewUser user) {
    return ImmutableUserStatus
      .builder()
      .build();
  }

}

