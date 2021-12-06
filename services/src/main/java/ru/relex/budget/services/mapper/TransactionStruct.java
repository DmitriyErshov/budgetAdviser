package ru.relex.budget.services.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.relex.budget.db.model.GroupModel;
import ru.relex.budget.db.model.TransactionModel;
import ru.relex.budget.services.model.transaction.ExistingMoneyTransaction;
import ru.relex.budget.services.model.transaction.NewMoneyTransaction;

import java.math.BigDecimal;
import java.time.Instant;

@Mapper
public interface TransactionStruct {
  @Mapping(target = "name", source = "transaction.name")
  @Mapping(target = "createdBy", source = "authorId")
  @Mapping(target = "amount", source = "transaction.amount")
  @Mapping(target = "groupId", source = "transaction.groupId")
  TransactionModel fromNewTransaction(NewMoneyTransaction transaction, long authorId);

  @Mapping(target = "name", source = "model.name")
  @Mapping(target = "createdBy", source = "model.createdBy")
  @Mapping(target = "amount", source = "model.amount")
  @Mapping(target = "groupId", source = "model.groupId")
  @Mapping(target = "id", source = "id")
  @Mapping(target = "createdAt", source = "createdAt")
  ExistingMoneyTransaction toExistingMoneyTransaction(TransactionModel model, long id, Instant createdAt);

  @Mapping(target = "name", source = "byId.name")
  @Mapping(target = "createdBy", source = "byId.createdBy")
  @Mapping(target = "amount", source = "byId.amount")
  @Mapping(target = "groupId", source = "byId.groupId")
  @Mapping(target = "id", source = "id")
  @Mapping(target = "createdAt", source = "createdAt")
  ExistingMoneyTransaction toExistingMoneyTransaction(TransactionModel byId);
}
