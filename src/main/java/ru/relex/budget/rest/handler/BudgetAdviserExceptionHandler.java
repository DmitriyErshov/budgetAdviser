package ru.relex.budget.rest.handler;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.relex.budget.rest.exception.DataIntegrityViolationException;
import ru.relex.budget.rest.exception.ObjectNotExistsException;
import ru.relex.budget.rest.exception.PSQLException;
import ru.relex.budget.rest.model.ErrorModel;
import ru.relex.budget.services.validation.ValidationErrors;

@ControllerAdvice
public class BudgetAdviserExceptionHandler {

  @ExceptionHandler(ObjectNotExistsException.class)
  ResponseEntity<?> handleNotFoundException(ObjectNotExistsException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  @ExceptionHandler(ConstraintViolationException.class)
  ResponseEntity<List<ErrorModel>> handleConstraintViolationException(ConstraintViolationException e) {
    final Set<ConstraintViolation<?>> errors = e.getConstraintViolations();
    final var errorModels = errors.stream()
      .map(err -> new ErrorModel(err.getMessage(), ValidationErrors.getMessageByCode(err.getMessage())))
      .collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorModels);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
  }

  @ExceptionHandler(PSQLException.class)
  ResponseEntity<?> handlePSQLException(PSQLException e) {
    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
  }
}
