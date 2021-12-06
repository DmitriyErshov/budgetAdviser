package ru.relex.budget.services.internal;

import ru.relex.budget.services.model.transaction.ExistingMoneyTransaction;
import ru.relex.budget.services.model.transaction.NewMoneyTransaction;

import java.util.List;

public interface TransactionService {

  ExistingMoneyTransaction createTransaction(NewMoneyTransaction transaction, long authorId);

  void addRecommendedSum(long id, Integer month, Integer year, long categoryId, double sum);

  List<ExistingMoneyTransaction> getTransactionsByCurrentMonth(long id);

  List<ExistingMoneyTransaction> getTransactionsByMonth(long id, Integer month, Integer year);

  ExistingMoneyTransaction getById(long id);

  void changeGroupBalance(long groupId, double sum);

  //update

  boolean deleteById(long id);
}
