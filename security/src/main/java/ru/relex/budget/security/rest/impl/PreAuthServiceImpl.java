package ru.relex.budget.security.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.relex.budget.commons.model.Role;
import ru.relex.budget.db.mapper.GroupMapper;
import ru.relex.budget.db.mapper.MoneyTransactionMapper;
import ru.relex.budget.db.mapper.UserAuthMapper;
import ru.relex.budget.db.model.TransactionModel;
import ru.relex.budget.db.model.UserProfile;
import ru.relex.budget.security.rest.PreAuthService;

@Service
public class PreAuthServiceImpl implements PreAuthService {

  private final UserProfile userProfile;

  private final MoneyTransactionMapper moneyTransactionMapper;

  private final GroupMapper groupMapper;

  private final UserAuthMapper userAuthMapper;

  @Autowired
  public PreAuthServiceImpl(UserProfile userProfile,
                            MoneyTransactionMapper moneyTransactionMapper,
                            GroupMapper groupMapper,
                            final UserAuthMapper userAuthMapper) {
    this.userProfile = userProfile;
    this.moneyTransactionMapper = moneyTransactionMapper;
    this.groupMapper = groupMapper;
    this.userAuthMapper = userAuthMapper;
  }

  @Override
  public boolean canGetUserInfo(long userId) {
    if (userProfile.getRole() == Role.ADMIN) {
      return true;
    }

    return userProfile.getId() == userId;
  }

  public boolean canGetGroupInfo(long groupId) {
    return userProfile.getRole() == Role.ADMIN || isUserGroupMember(groupId);
  }

  @Override
  public boolean canUserActWithTransaction(long id) {

    TransactionModel transactionModel = moneyTransactionMapper.findById(id);
    if (transactionModel == null) {
      return false;
    }
    //проверяем что юзер является либо создаетелем либо админом группы
    return (transactionModel.getCreatedBy() == userProfile.getId()) ||
      (isUserGroupAdmin(transactionModel.getGroupId()));
  }

  @Override
  public boolean isUserGroupMember(long id) {
    return userAuthMapper.isUserGroupMember(userProfile.getId(), id);
  }

  @Override
  public boolean isUserGroupAdmin(long id) {
    return userAuthMapper.isUserGroupAdmin(userProfile.getId(), id, Role.GROUP_ADMIN.getId());
  }

  @Override
  public boolean canUserGetTransactionInfo(long transactionId) {

    TransactionModel transactionModel = moneyTransactionMapper.findById(transactionId);

    if (transactionModel == null) {
      return false;
    }

    return isUserGroupMember(transactionModel.getGroupId());
  }
}
