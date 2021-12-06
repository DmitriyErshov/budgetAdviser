package ru.relex.budget.db.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {
  "groupName",
  "description",
  "createdAt",
  "createdBy",
  "balance",
})
public class GroupMemberModel {
  private String groupName;
  private String description;
  private int admin_id;
}
