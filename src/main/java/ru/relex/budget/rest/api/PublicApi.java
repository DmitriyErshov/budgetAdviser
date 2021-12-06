package ru.relex.budget.rest.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.relex.budget.services.facade.TransactionFacade;
import ru.relex.budget.services.facade.UserFacade;
import ru.relex.budget.services.model.group.ExistingGroup;
import ru.relex.budget.services.model.transaction.ExistingMoneyTransaction;
import ru.relex.budget.services.model.user.ExistingUser;
import ru.relex.budget.rest.exception.ObjectNotExistsException;
import ru.relex.budget.services.model.user.NewUser;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping(
  value = "/public",
  produces = "application/json"
)
public class PublicApi {

  private static final Logger logger = LoggerFactory.getLogger(UserApi.class);

  private final UserFacade userFacade;

  private final TransactionFacade transactionFacade;


  @Autowired
  public PublicApi(final UserFacade userFacade,
                   final TransactionFacade transactionFacade) {
    this.userFacade = userFacade;
    this.transactionFacade = transactionFacade;
  }


  @PostMapping
  @RequestMapping(
    value = "/reg",
    consumes = "application/json"
  )
  @ResponseStatus(HttpStatus.CREATED)
  ExistingUser createUser(@RequestBody final NewUser user) {
    logger.info("Consumed: {}", user);
    return userFacade.createUser(user);
  }


  @GetMapping
  @RequestMapping(
    value = "/getCurrentUser"
  )
  ExistingUser getCurrentUser() {

    final var user = userFacade.getCurrentUser();
    if (user == null) {
      throw new ObjectNotExistsException();
    }

    return user;
  }

  //for detecting last id in 'user' table
  /*@GetMapping
  @RequestMapping(
    value = "/getNewId"
  )
  int getNewId() {
    return userFacade.getNewId()+1;
  }
*/
  @GetMapping
  @RequestMapping(
    value = "/homePage"
  )
  @ResponseStatus(HttpStatus.ACCEPTED)
  String getHomePage() {
    //возвращает html страничку с homePage
    return "Welcome to Budget Adviser";
  }

  @PostMapping
  @RequestMapping(
    value = "/activateAccount/{activationCode}"
  )
  boolean activateAccount(@PathVariable ("activationCode") String activationCode) {
    return userFacade.activateAccount(activationCode);
  }

}
