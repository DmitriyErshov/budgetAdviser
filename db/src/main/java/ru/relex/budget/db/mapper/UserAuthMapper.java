package ru.relex.budget.db.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import ru.relex.budget.db.model.UserModel;
import ru.relex.budget.db.model.UserProfile;

import java.util.Optional;

@Mapper
public interface UserAuthMapper {

  @Select(""+
    "SELECT u.user_id AS id, " +
    "       username," +
    "       password," +
    "       is_locked AS locked," +
    "       is_active AS active," +
    "       global_role_id AS role "+
    "FROM users u " +
    //"LEFT JOIN global_roles gr on u.user_id = gr.global_role_id " +
    "WHERE lower(username) = lower(#{searchString}) " +
    "   OR lower(email) = lower(#{searchString}) " +
    "   OR phone = (#{searchString})"
  )
  Optional<UserModel> getUserByUsernameOrEmailOrPhone(String searchString);

  @Select(
    "SELECT u.user_id AS id, " +
      "       username," +
      "       first_name," +
      "       last_name," +
      "       email," +
      "       phone," +
      "       global_role_id AS role " +
      "FROM users u " +
      //"LEFT JOIN user_roles ur on u.user_id = ur.user_id " +
      "WHERE username = #{username}"
  )
  UserProfile getProfileInfo(String username);


  boolean isUserGroupMember(@Param("userId") long userId, @Param("groupId") long groupId);

  boolean isUserGroupAdmin(@Param("userId") long userId, @Param("groupId") long groupId, @Param("roleAdminId") long roleAdminId);
}
