package ru.relex.budget.rest.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.relex.budget.rest.exception.ObjectNotExistsException;
import ru.relex.budget.services.facade.UserFacade;
import ru.relex.budget.services.model.user.ExistingUser;
import ru.relex.budget.services.model.user.NewUser;
import ru.relex.budget.services.model.user.UpdatableUser;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping(
  value = "/users",
  produces = "application/json"
)
public class UserApi {

  private static final Logger logger = LoggerFactory.getLogger(UserApi.class);

  private final UserFacade userFacade;

  @Autowired
  public UserApi(final UserFacade userFacade) {
    this.userFacade = userFacade;
  }


  @PostMapping(path = "/")
  @RequestMapping(
    consumes = "application/json"
  )
  @ResponseStatus(HttpStatus.CREATED)
  ExistingUser createUser(@RequestBody final NewUser user) {
    logger.info("Consumed: {}", user);
    return userFacade.createUser(user);
  }


  @PostMapping(path = "/ban/{id}")
  boolean banUser(@PathVariable("id") long id) {
    return userFacade.banUser(id);
  }


  @GetMapping(path = "/")
  @RolesAllowed("ROLE_ADMIN")
  List<ExistingUser> getAllUsers() {
    return userFacade.getAllUsers();
  }

  @GetMapping(path = "/{id}")
  @PreAuthorize("@preAuthServiceImpl.canGetUserInfo(#id)")
  ExistingUser getById(@PathVariable("id") long id) {

    final var user = userFacade.getById(id);
    if (user == null) {
      throw new ObjectNotExistsException();
    }

    return user;
  }


  @PutMapping(path = "/{id}")
  @RolesAllowed({"ROLE_ADMIN","ROLE_USER"})
  @PreAuthorize("@preAuthServiceImpl.canGetUserInfo(#id)")
  ExistingUser updateUser(@PathVariable("id") long id,
                          @RequestBody UpdatableUser updatableUser) {
    final var user = userFacade.update(id, updatableUser);

    if (user == null) {
      throw new ObjectNotExistsException();
    }

    return user;
  }

  //@RolesAllowed("ROLE_ADMIN")
  @DeleteMapping(path = "/{id}")
  void deleteUser(@PathVariable("id") long id) {
    if (userFacade.deleteById(id)) {
      return;
    }

    throw new ObjectNotExistsException();
  }
}
