package ru.relex.budget.db.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import ru.relex.budget.db.model.UserModel;

import java.util.List;

@Mapper
public interface UserMapper {

  UserModel createUser(UserModel user);

  void setUserCreatedBy(@Param("userId") long userId, @Param("createdBy") long createdBy);

  boolean banUserById(@Param("id") long userId);

  void addActivationCode(@Param("userId") long userId, @Param("code") String code);

  void activateAccount(@Param("userId") long userId);

  //language=PostgreSQL
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
    "       email," +
    "       global_role_id AS role " +
    "FROM users u " +
    "WHERE user_id = #{id}"
  )
  UserModel findById(@Param("id") long id);

  boolean isUserExists(@Param("id") long id);

  @Select(" " +
    "SELECT user_id " +
    "FROM users_activation_codes " +
    "WHERE activation_code = #{code}"
  )
  Long findByActivationCode(@Param("code") String code);


  void deleteActivationCode(@Param("id") long userId);

  //language=PostgreSQL
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
    "       email," +
    "       global_role_id AS role " +
    "FROM users u"
  )
  List<UserModel> findAll();

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
    "       email," +
    "       global_role_id AS role " +
    "FROM users u " +
    "LIMIT #{limit}"
  )
  List<UserModel> findUsersWithLimitCount(@Param("limit") long userId);

  //FIXME должно быть в RoleMapper
  //void saveUserRole(@Param("userId") long userId, @Param("role") Role role);

  void updateUser(UserModel model);

  void deleteUserById(@Param("id") long id);

  //language=PostgreSQL
  @Select(""+
  "SELECT user_id as id FROM users ORDER BY user_id DESC LIMIT 1 ")
  UserModel getNewId();
}
