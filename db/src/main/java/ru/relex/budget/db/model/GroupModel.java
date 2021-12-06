package ru.relex.budget.db.model;

import lombok.*;

  import java.math.BigDecimal;
  import java.time.Instant;

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
public class GroupModel {

  private long id;

  private String groupName;
  private String description;

  private Instant createdAt;
  private long createdBy;

  private double balance;

  private String joinCode;
}

