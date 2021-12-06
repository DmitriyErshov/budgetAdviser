package ru.relex.budget.rest.model;

public class ErrorModel {

  private final String code;
  private final String message;

  public ErrorModel(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
