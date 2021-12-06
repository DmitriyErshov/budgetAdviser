package ru.relex.budget.db.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import ru.relex.budget.db.model.GroupModel;
import ru.relex.budget.db.model.TransactionModel;

import java.time.Instant;
import java.util.List;

@Mapper
public interface MoneyTransactionMapper {

  TransactionModel createTransaction(TransactionModel transaction);

  void createRowsInMoneyExpenditure(@Param("groupId")long groupId, @Param("createdAt")Instant createdAt);

  boolean isTransactionExists(@Param("id")long id);

  void addRecommendedSum(@Param("groupId")long id, @Param("lowBound") String lowBound,
                         @Param("highBound") String highBound, @Param("categoryId")long categoryId,
                         @Param("sum") double sum);

  void addAmountToCategory(@Param("sum") double sum, @Param("lowBound") String lowBound,
                           @Param("highBound") String highBound,
                           @Param("groupId") long groupId, @Param("categoryId") long categoryId);


  //language=postgreSQL
  @Select("" +
    "SELECT t.transaction_id AS id, " +
    "       name," +
    "       createdby," +
    "       created_at," +
    "       group_id," +
    "       amount," +
    "       category_id " +
    "FROM money_transactions t " +
    "WHERE transaction_id = #{id}"
  )
  TransactionModel findById(@Param("id") long id);

  @Select("" +
    "SELECT is_income " +
    "FROM category " +
    "WHERE category_id = #{id}"
  )boolean isTransactionIncome(@Param("id") long id);

  //language=postgreSQL
  @Select("" +
    "SELECT t.transaction_id AS id, " +
    "       name," +
    "       createdBy," +
    "       created_at," +
    "       group_id," +
    "       amount," +
    "       category_id " +
    "FROM money_transactions t " +
    "WHERE group_id = #{id} AND t.created_at BETWEEN #{lowBound}::timestamp AND #{highBound}::timestamp"
  )
  List<TransactionModel> findByCurrentMonth(@Param("id") long id,
                                            @Param("lowBound") String lowBound,
                                            @Param("highBound") String highBound);

  //language=postgreSQL
  @Select("" +
    "SELECT t.transaction_id AS id, " +
    "       name," +
    "       createdBy," +
    "       created_at," +
    "       group_id," +
    "       amount," +
    "       category_id " +
    "FROM money_transactions t " +
    "WHERE group_id = #{id} AND t.created_at BETWEEN #{lowBound}::timestamp AND #{highBound}::timestamp " +
    "LIMIT 2"
  )
  List<TransactionModel> findOneByCurrentMonth(@Param("id") long id, @Param("lowBound") String lowBound, @Param("highBound") String highBound);

  void deleteTransactionById(@Param("id") long id);


}

