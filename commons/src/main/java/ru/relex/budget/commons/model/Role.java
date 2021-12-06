package ru.relex.budget.commons.model;

public enum Role {

  ADMIN(1),
  USER(2),
  GROUP_ADMIN(3),
  GROUP_USER(4);

  private final int id;

  Role(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public static Role fromId(Integer id) {
    if (id == null) {
      return null;
    }

    for (var value: values()) {
      if (id.equals(value.id)) {
        return value;
      }
    }

    return null;
  }
}
