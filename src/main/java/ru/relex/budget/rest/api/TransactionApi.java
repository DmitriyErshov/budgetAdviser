package ru.relex.budget.rest.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.relex.budget.rest.exception.ObjectNotExistsException;
import ru.relex.budget.services.facade.TransactionFacade;
import ru.relex.budget.services.model.transaction.ExistingMoneyTransaction;
import ru.relex.budget.services.model.transaction.NewMoneyTransaction;
import ru.relex.budget.services.model.transaction.UpdatableMoneyTransaction;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping(
  value = "/cashFlow",
  produces = "application/json"
)
public class TransactionApi {

  private static final Logger logger = LoggerFactory.getLogger(TransactionApi.class);

  public final TransactionFacade transactionFacade;

  @Autowired
  public TransactionApi(final TransactionFacade transactionFacade) {
    this.transactionFacade = transactionFacade;
  }

  @PostMapping
  @RequestMapping(
    consumes = "application/json"
  )
  @PreAuthorize("@preAuthServiceImpl.isUserGroupMember(#transaction.groupId)")
  @ResponseStatus(HttpStatus.CREATED)
  ExistingMoneyTransaction createTransaction(@RequestBody final NewMoneyTransaction transaction) {
    logger.info("Consumed: {}", transaction);
    return transactionFacade.createMoneyTransaction(transaction);
  }


  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(
    value = "/setRecommendedMonthSum"
  )
  @PreAuthorize("@preAuthServiceImpl.isUserGroupAdmin(#id)")
  void setRecommendedSum(@RequestParam ("id") long id, @RequestParam ("month") Integer month, @RequestParam ("year") Integer year, @RequestParam ("categoryId") long categoryId,  @RequestParam ("sum") double sum)  {
    transactionFacade.addRecommendedSum(id, month, year, categoryId, sum);
  }

  @GetMapping(path = "/{id}")
  @PreAuthorize("@preAuthServiceImpl.canUserGetTransactionInfo(#id)")
  ExistingMoneyTransaction getById(@PathVariable("id") long id) {

    final var transaction = transactionFacade.getById(id);
    if (transaction == null) {
      throw new ObjectNotExistsException();
    }

    return transaction;
  }


  @RolesAllowed("ROLE_ADMIN")
  @PreAuthorize("@preAuthServiceImpl.isUserGroupMember(#id)")
  @RequestMapping(
    value = "/getCurrentGroupCashFlows/{id}"
  )
  List<ExistingMoneyTransaction> getGroupTransactions(@PathVariable ("id") long id) {

    List<ExistingMoneyTransaction> transactions = transactionFacade.getTransactionsByCurrentMonth(id);

    return transactions;

//    if (transactions.size() > 0) {
//      return transactions;
//    }else{
//      throw new ObjectNotExistsException();
//    }
  }

  @GetMapping
  @RequestMapping(
    value = "/getGroupCashFlows"
  )
  @PreAuthorize("@preAuthServiceImpl.isUserGroupMember(#id)")
  List<ExistingMoneyTransaction> getGroupTransactionsByMonth(@RequestParam ("id") long id,
                                                             @RequestParam ("month") Integer month,
                                                             @RequestParam ("year") Integer year) {

    List<ExistingMoneyTransaction> members = transactionFacade.getTransactionsByMonth(id, month, year);

    if (members.size() > 0) {
      return members;
    }else{
      throw new ObjectNotExistsException();
    }
  }


  @PutMapping(path = "/{id}")
  @RolesAllowed("ROLE_ADMIN")
  @PreAuthorize("@preAuthServiceImpl.canUserActWithTransaction(#id)")
  ExistingMoneyTransaction update(@PathVariable ("id") long id,
                                  @RequestBody UpdatableMoneyTransaction updatableTransaction){
    var transaction = transactionFacade.update(id,updatableTransaction);
    if(transaction == null){
      throw new ObjectNotExistsException();
    }
    return transaction;
  }


  @DeleteMapping(path = "/{id}")
  @RolesAllowed("ROLE_ADMIN")
  @PreAuthorize("@preAuthServiceImpl.canUserActWithTransaction(#id)")
  void deleteTransaction(@PathVariable("id") long id) {
    if (transactionFacade.deleteById(id)) {
      return;
    }

    throw new ObjectNotExistsException();
  }
}
