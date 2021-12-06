package ru.relex.budget.rest.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.relex.budget.rest.exception.DataIntegrityViolationException;
import ru.relex.budget.rest.exception.ObjectNotExistsException;
import ru.relex.budget.services.model.group.UpdatableGroup;
import ru.relex.budget.services.model.user.ExistingUser;
import ru.relex.budget.services.facade.GroupFacade;
import ru.relex.budget.services.model.group.ExistingGroup;
import ru.relex.budget.services.model.group.NewGroup;
import ru.relex.budget.commons.model.Role;

import javax.annotation.security.RolesAllowed;
import java.util.List;


@RestController
@RequestMapping(
  value = "/groups",
  produces = "application/json"
)
public class GroupApi {

  private static final Logger logger = LoggerFactory.getLogger(GroupApi.class);

  private final GroupFacade groupFacade;

  @Autowired
  public GroupApi(GroupFacade groupFacade) {
    this.groupFacade = groupFacade;
  }

  @PostMapping
  @RequestMapping(
    consumes = "application/json"
  )
  @ResponseStatus(HttpStatus.CREATED)
  ExistingGroup createGroup(@RequestBody final NewGroup group){
    logger.info("Consumed: {}", group);
    return groupFacade.createGroup(group);
  }

  //change to bool type
  //TODO /{id}/members
  @PostMapping(path = "/members")
  @ResponseStatus(HttpStatus.CREATED)
//  @RequestMapping(
//    value = "/addMember"
//  )
  //@PreAuthorize("@preAuthServiceImpl.isUserGroupAdmin(#groupId)")
  //@RolesAllowed("ROLE_ADMIN")
  void addMemberToGroup(@RequestParam  ("groupId") long groupId,
                        @RequestParam ("userId") long userId,
                        @RequestParam ("role") Role role){
    //groupFacade.addGroupMember(groupId, userId, role);
    if(groupFacade.addGroupMember(groupId, userId, role)){
      return;
    }
    //throw new DataIntegrityViolationException();

    //final var group = groupFacade.getById(groupId);
    //return group;
  }

  @DeleteMapping(path = "/members")
  @RolesAllowed("ROLE_ADMIN")
  @PreAuthorize("@preAuthServiceImpl.isUserGroupAdmin(#groupId)")
  void deleteMemberToGroup(@RequestParam  ("groupId") long groupId,
                           @RequestParam ("userId") long userId){
    //groupFacade.addGroupMember(groupId, userId, role);
    if(groupFacade.deleteGroupMember(groupId, userId)){
      return;
    }
    throw new DataIntegrityViolationException();

    //final var group = groupFacade.getById(groupId);
    //return group;
  }

  //TODO to
  @PostMapping
  @RequestMapping(
    value = "/joinToGroup/{joinCode}"
  )
  void joinToGroup(@PathVariable ("joinCode") String joinCode) {
    groupFacade.addGroupMemberWithJoinCode(joinCode);
  }

  @GetMapping(path = "/{id}")
  @PreAuthorize("@preAuthServiceImpl.canGetGroupInfo(#groupId)")
  ExistingGroup getGroupById(@PathVariable ("id") long groupId){

    final var group = groupFacade.getById(groupId);
    if(group == null){
      throw new ObjectNotExistsException();
    }

    return group;
  }

  @GetMapping(path = "/userGroups/{id}")
  List<ExistingGroup> getGroupsByUser(@PathVariable ("id") long userId){
    List<ExistingGroup> groups = groupFacade.getGroupsByUser(userId);

    if(groups.size() > 0) {
      return groups;
    }else{
      return null;
      //exception
    }
  }

  @GetMapping(path = "/getMembers/{id}")
  @PreAuthorize("@preAuthServiceImpl.isUserGroupMember(#id)")
  List<ExistingUser> getGroupMembers(@PathVariable ("id") long id){

    List<ExistingUser> members = groupFacade.getGroupMembers(id);

    if(members.size() > 0) {
      return members;
    }else{
      return null;
      //exception
    }
  }





  @DeleteMapping(path = "/{id}")
  @RolesAllowed("ROLE_ADMIN")
  //@PreAuthorize("@preAuthServiceImpl.isUserGroupAdmin(#id)")
  void delete(@PathVariable ("id") long id){
    if(groupFacade.deleteById(id)){
      return;
    }
    throw new ObjectNotExistsException();
  }

  @GetMapping(path = "/")
  @RolesAllowed("ROLE_ADMIN")
  List<ExistingGroup> getAllGroups(){
    return groupFacade.getAllGroups();
  }

}
