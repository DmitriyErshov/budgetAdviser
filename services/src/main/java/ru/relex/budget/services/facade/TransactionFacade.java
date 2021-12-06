package ru.relex.budget.services.facade;

import org.hibernate.validator.constraints.Range;
import ru.relex.budget.services.meta.Facade;
import ru.relex.budget.services.model.transaction.ExistingMoneyTransaction;
import ru.relex.budget.services.model.transaction.NewMoneyTransaction;
import ru.relex.budget.services.model.transaction.UpdatableMoneyTransaction;
import ru.relex.budget.services.validation.annotations.GroupExists;
import ru.relex.budget.services.validation.annotations.TransactionExists;

import javax.validation.Valid;
import java.util.List;

import static ru.relex.budget.services.validation.ValidationErrors.*;

public interface TransactionFacade {

  ExistingMoneyTransaction createMoneyTransaction(@Valid NewMoneyTransaction transaction);

  ExistingMoneyTransaction getById(@TransactionExists(message = "transaction doesn't exist") long id);

  void addRecommendedSum(@GroupExists(message = "group does't exist") long groupId,
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
                           message = CATEGORY_ID_IS_INVALID) double sum);

  List<ExistingMoneyTransaction> getTransactionsByCurrentMonth(@GroupExists(message = "group does't exist") long groupId);

  List<ExistingMoneyTransaction> getTransactionsByMonth(@GroupExists(message = "group does't exist") long groupId,
                                                        @Range(min = 1,
                                                          max = 12,
                                                          message = MONTH_NUM_IS_INVALID) Integer month,
                                                        @Range(min = 1997,
                                                          max = 3000,
                                                          message = YEAR_NUM_IS_INVALID) Integer year);

  ExistingMoneyTransaction update(@TransactionExists(message = "transaction doesn't exist") long id,
                                  UpdatableMoneyTransaction transaction);

  boolean deleteById(@TransactionExists(message = "transaction doesn't exist") long id);
}
