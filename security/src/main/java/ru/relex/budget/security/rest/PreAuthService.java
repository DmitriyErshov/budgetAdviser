package ru.relex.budget.security.rest;

public interface PreAuthService {

  boolean canGetUserInfo(long userId);

  boolean canGetGroupInfo(long userId);

  boolean canUserActWithTransaction(long id);

  boolean isUserGroupMember(long id);

  boolean isUserGroupAdmin(long id);

  boolean canUserGetTransactionInfo(long transactionId);
}
