package ru.relex.budget.rest.exception;

public class DataIntegrityViolationException extends RuntimeException{

  public DataIntegrityViolationException() {
    super(null, null, false, false);
  }

}

