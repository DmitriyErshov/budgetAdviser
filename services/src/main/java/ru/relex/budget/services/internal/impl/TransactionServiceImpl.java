package ru.relex.budget.services.internal.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.relex.budget.db.mapper.GroupMapper;
import ru.relex.budget.db.mapper.MoneyTransactionMapper;
import ru.relex.budget.db.mapper.UserMapper;
import ru.relex.budget.db.model.TransactionModel;
import ru.relex.budget.db.model.UserModel;
import ru.relex.budget.services.internal.TransactionService;
import ru.relex.budget.services.mapper.TransactionStruct;
import ru.relex.budget.services.mapper.UserStruct;
import ru.relex.budget.services.model.transaction.ExistingMoneyTransaction;
import ru.relex.budget.services.model.transaction.NewMoneyTransaction;
import ru.relex.budget.services.model.user.ExistingUser;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

  private final TransactionStruct transactionStruct;

  private final MoneyTransactionMapper moneyTransactionMapper;

  private final GroupMapper groupMapper;

  @Autowired
  public TransactionServiceImpl(final TransactionStruct transactionStruct,
                                final MoneyTransactionMapper moneyTransactionMapper,
                                final GroupMapper groupMapper) {
    this.transactionStruct = transactionStruct;
    this.moneyTransactionMapper = moneyTransactionMapper;
    this.groupMapper = groupMapper;
  }


  @Override
  public ExistingMoneyTransaction createTransaction(NewMoneyTransaction transaction, long authorId) {

    final var model = transactionStruct.fromNewTransaction(transaction, authorId);

    //request to db
    TransactionModel newTransaction = moneyTransactionMapper.createTransaction(model);

    //проверить, что транзакция первая в этом месяце
    Instant instant = Instant.now();
    String currentDate = instant.toString();
    String currentMonth = currentDate.substring(0, 7);
    String lowBound = currentMonth + "-1 00:00:00";
    String highBound = currentMonth + "-31 00:00:00";

    LocalDate localDate;
    LocalDate.of(2020, 8, 1).plus(1, ChronoUnit.MONTHS).minus(1, ChronoUnit.DAYS);

    //проверить, что транзакция первая в этом месяце
    if(moneyTransactionMapper.findOneByCurrentMonth(transaction.getGroupId(), lowBound, highBound).size() == 1) {
      moneyTransactionMapper.createRowsInMoneyExpenditure(model.getGroupId(), newTransaction.getCreatedAt()); //то создать 12 строк в money_expediture
    }

    //занести данные в money_expediture
    moneyTransactionMapper.addAmountToCategory(transaction.getAmount(), lowBound, highBound, transaction.getGroupId(),
                                                                                    transaction.getCategoryId());
    //изменить баланс группы
    groupMapper.changeGroupBalance(transaction.getAmount(),
                                  moneyTransactionMapper.isTransactionIncome(transaction.getCategoryId()),
                                  transaction.getGroupId());

    return transactionStruct.toExistingMoneyTransaction(model, newTransaction.getId(), newTransaction.getCreatedAt());
  }

  @Override
  public void addRecommendedSum(long id, Integer monthNum, Integer yearNum, long categoryId, double sum) {
    String month = monthNum.toString();
    String year = yearNum.toString();

    String lowBound = year + '-' + month + '-' + "-1 00:00:00";
    String highBound = year + '-' + month + '-' + "-31 00:00:00";

    moneyTransactionMapper.addRecommendedSum(id, lowBound, highBound, categoryId, sum);
  }

  @Override
  //получение списка денежных операций за текущий месяц
  public List<ExistingMoneyTransaction> getTransactionsByCurrentMonth(long id) {

    Instant instant = Instant.now();
    String currentDate = instant.toString();
    String currentMonth = currentDate.substring(0, 7);
    String lowBound = currentMonth + "-1 00:00:00";
    String highBound = currentMonth + "-31 00:00:00";
    List<TransactionModel> models = moneyTransactionMapper.findByCurrentMonth(id, lowBound, highBound);

    List<ExistingMoneyTransaction> transactions = new ArrayList<>();
    for (TransactionModel model : models) {
      transactions.add(transactionStruct.toExistingMoneyTransaction(model));
    }

    return transactions;
  }

  @Override
  public List<ExistingMoneyTransaction> getTransactionsByMonth(long id, Integer monthNum, Integer yearNum) {

    String month = monthNum.toString();
    String year = yearNum.toString();

    String lowBound = year + '-' + month + '-' + "-1 00:00:00";
    String highBound = year + '-' + month + '-' + "-31 00:00:00";

    List<TransactionModel> models = moneyTransactionMapper.findByCurrentMonth(id, lowBound, highBound);

    List<ExistingMoneyTransaction> transactions = new ArrayList<>();
    for (TransactionModel model : models) {
      transactions.add(transactionStruct.toExistingMoneyTransaction(model));
    }

    return transactions;
  }

  @Override
  public ExistingMoneyTransaction getById(long id) {
    return transactionStruct.toExistingMoneyTransaction(moneyTransactionMapper.findById(id));
  }

  @Override
  public void changeGroupBalance(long groupId, double sum) {

  }

  @Override
  public boolean deleteById(long id) {
    TransactionModel transaction = moneyTransactionMapper.findById(id);

    //вычитаем сумму этой операции из таблицы категорий
    Instant instant = transaction.getCreatedAt();
    String currentDate = instant.toString();
    String currentMonth = currentDate.substring(0, 7);
    String lowBound = currentMonth + "-1 00:00:00";
    String highBound = currentMonth + "-31 00:00:00";

    moneyTransactionMapper.addAmountToCategory(-transaction.getAmount(),
      lowBound, highBound, transaction.getGroupId(), transaction.getCategoryId());

    //TODO сделать изенение баланса группы при удалении транзакции
    //изменить баланс группы(инвертируем isTransactionIncome для осуществления обратных операция списывания и зачисления
    groupMapper.changeGroupBalance(transaction.getAmount(),
                                  !moneyTransactionMapper.isTransactionIncome(transaction.getCategoryId()),
                                  transaction.getGroupId());

    moneyTransactionMapper.deleteTransactionById(id);

    return true;
  }
}
