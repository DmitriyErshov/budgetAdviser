package ru.relex.budget.services.facade.impl;

import org.hibernate.validator.constraints.Range;
import ru.relex.budget.db.model.UserProfile;
import ru.relex.budget.services.facade.TransactionFacade;
import ru.relex.budget.services.internal.TransactionService;
import ru.relex.budget.services.meta.Facade;
import ru.relex.budget.services.model.transaction.ExistingMoneyTransaction;
import ru.relex.budget.services.model.transaction.NewMoneyTransaction;
import ru.relex.budget.services.model.transaction.UpdatableMoneyTransaction;
import ru.relex.budget.services.validation.annotations.GroupExists;
import ru.relex.budget.services.validation.annotations.TransactionExists;

import javax.validation.Valid;
import java.util.List;

import static ru.relex.budget.services.validation.ValidationErrors.*;

@Facade
public class TransactionFacadeImpl implements TransactionFacade {

  private final TransactionService transactionService;
  private final UserProfile currentUser;

  public TransactionFacadeImpl(final TransactionService transactionService,
                               final UserProfile currentUser) {
    this.transactionService = transactionService;
    this.currentUser = currentUser;
  }

  @Override
  public ExistingMoneyTransaction createMoneyTransaction(@Valid NewMoneyTransaction transaction) {
    return transactionService.createTransaction(transaction, currentUser.getId());
  }

  @Override
  public ExistingMoneyTransaction getById(@TransactionExists(message = "transaction doesn't exist") long id) {
    return transactionService.getById(id);
  }

  @Override
  public void addRecommendedSum(@GroupExists(message = "group does't exist") long groupId,
                                @Range(min = 1,
                                  max = 12,
                                  message = MONTH_NUM_IS_INVALID) Integer month,
                                @Range(min = 1997,
                                  max = 3000,
                                  message = YEAR_NUM_IS_INVALID) Integer year,
                                @Range(min = 1,
                                  max = 12,
                                  message = CATEGORY_ID_IS_INVALID) long categoryId,
                                @Range(min = 1,
                                  max = 2000000000,
                                  message = CATEGORY_ID_IS_INVALID) double sum) {
    transactionService.addRecommendedSum(groupId, month, year, categoryId, sum);
  }

  @Override
  public List<ExistingMoneyTransaction> getTransactionsByCurrentMonth(@GroupExists(message = "group does't exist") long groupId) {
    return transactionService.getTransactionsByCurrentMonth(groupId);
  }

  @Override
  public List<ExistingMoneyTransaction> getTransactionsByMonth(@GroupExists(message = "group does't exist") long groupId,
                                                               @Range(min = 1,
                                                                 max = 12,
                                                                 message = MONTH_NUM_IS_INVALID) Integer month,
                                                               @Range(min = 1997,
                                                                 max = 3000,
                                                                 message = YEAR_NUM_IS_INVALID) Integer year) {
    return transactionService.getTransactionsByMonth(groupId, month, year);
  }


  @Override
  public ExistingMoneyTransaction update(@TransactionExists(message = "transaction doesn't exist") long id,
                                         UpdatableMoneyTransaction transaction) {
    //!!! реализовать update в transactionService
    return null;
  }

  @Override
  public boolean deleteById(@TransactionExists(message = "transaction doesn't exist") long id) {
    return transactionService.deleteById(id);
  }
}
