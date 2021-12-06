package ru.relex.budget.db.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import ru.relex.budget.commons.model.Role;
import ru.relex.budget.db.model.GroupMemberModel;
import ru.relex.budget.db.model.GroupModel;
import ru.relex.budget.db.model.UserModel;

import java.util.List;

@Mapper
public interface GroupMapper {

  GroupModel createGroup(GroupModel group);

  //language=PostgreSQL
  @Select("" +
    "SELECT g.group_id AS id, " +
    "       group_name," +
    "       description," +
    "       created_at," +
    "       created_by," +
    "       balance, " +
    "       join_code " +
    "FROM groups g " +
    "WHERE group_id = #{id}"
  )
  GroupModel findById(@Param("id") long id);

  //language=PostgreSQL
  @Select("" +
    "SELECT group_id " +
    "FROM group_members " +
    "WHERE user_id = #{id}"
  )
  int findByGroupAdmin(@Param("id") long id);

  //language=postgreSQL
  @Select("" +
    "SELECT u.user_id AS id, " +
    "       username," +
    "       first_name," +
    "       last_name," +
    "       created_by," +
    "       created_at," +
    "       is_locked AS locked," +
    "       is_active AS active," +
    "       phone," +
    "       email, " +
    "       global_role_id AS role " +
    "FROM users u INNER JOIN group_members g " +
    "ON u.user_id = g.user_id " +
    "WHERE group_id = #{id}"
  )
  List<UserModel> findGroupMembersById(@Param("id") long groupId);

  @Select("" +
    "SELECT g.group_id AS id, " +
    "       group_name," +
    "       description," +
    "       created_at," +
    "       created_by," +
    "       balance, " +
    "       join_code " +
    "FROM groups g " +
    "WHERE join_code = #{joinCode}"
  )
  GroupModel findByJoinCode(@Param("joinCode") String joinCode);


  void changeGroupBalance(@Param("sum") double sum, @Param("isIncome") boolean isIncome, @Param("groupId") long groupId);

  GroupModel updateGroup(GroupModel model);


  //in-dev
  //роли на
  void saveGroupMember(@Param("userId") long userId, @Param("groupId") long groupId, @Param("roleId") Role role);

  void deleteGroupMember(@Param("userId") long userId, @Param("groupId") long groupId);

  void deleteGroupById(@Param("id") long groupId);

  @Select("" +
    "SELECT g.group_id AS id, " +
    "       group_name," +
    "       description," +
    "       created_at," +
    "       created_by," +
    "       balance, " +
    "       join_code " +
    "FROM groups g "
  )
  List<GroupModel> findAll();

  @Select("" +
    "SELECT g.group_id AS id, " +
    "       group_name," +
    "       description," +
    "       created_at," +
    "       created_by," +
    "       balance, " +
    "       join_code " +
    "FROM groups g INNER JOIN group_members m " +
    "ON g.group_id = m.group_id " +
    "WHERE m.user_id = #{id}"
  )
  List<GroupModel> findGroupsByUserId(@Param("id") long userId);

  boolean isGroupExists(@Param("id") long id);
}
