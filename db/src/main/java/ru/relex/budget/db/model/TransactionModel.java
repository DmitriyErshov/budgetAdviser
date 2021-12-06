package ru.relex.budget.db.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {
  "name",
  "createdAt",
  "createdBy",
  "groupId",
  "sum"
})
public class TransactionModel {
  private long id;

  private String name;

  private long createdBy;

  private long groupId;

  private double amount;

  private long categoryId;

  private Instant createdAt;
}
