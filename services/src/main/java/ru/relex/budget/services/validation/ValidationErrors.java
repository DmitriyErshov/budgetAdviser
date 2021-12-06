package ru.relex.budget.services.validation;

import java.util.Map;

public final class ValidationErrors {
  public static final String EMAIL_HAS_INVALID_FORMAT = "EMAIL_HAS_INVALID_FORMAT";

  private ValidationErrors() {
  }

  public static final String LAST_NAME_LENGTH_IS_INVALID = "LAST_NAME_LENGTH_IS_INVALID";
  public static final String FIRST_NAME_LENGTH_IS_INVALID = "FIRST_NAME_LENGTH_IS_INVALID";
  public static final String PERSONAL_INFO_MUST_BE_SET = "PERSONAL_INFO_MUST_BE_SET";
  public static final String ROLE_MUST_BE_SET = "ROLE_MUST_BE_SET";
  public static final String PHONE_FORMAT_IS_INVALID = "PHONE_FORMAT_IS_INVALID";
  public static final String GROUP_NAME_MUST_BE_SET = "GROUP_NAME_MUST_BE_SET";
  public static final String USERNAME_MUST_BE_SET = "USERNAME_MUST_BE_SET";
  public static final String TRANSACTION_NAME_MUST_BE_SET = "TRANSACTION_NAME_MUST_BE_SET";
  public static final String CATEGORY_ID_IS_INVALID = "CATEGORY_ID_IS_INVALID";
  public static final String MONTH_NUM_IS_INVALID = "MONTH_NUM_IS_INVALID";
  public static final String YEAR_NUM_IS_INVALID = "MONTH_NUM_IS_INVALID";
  public static final String DAY_NUM_IS_INVALID = "DAY_NUM_IS_INVALID";


  private static final Map<String, String> ERRORS = Map.ofEntries(
    Map.entry(ROLE_MUST_BE_SET, "Role must be set"),
    Map.entry(PERSONAL_INFO_MUST_BE_SET, "Personal info must be set")
  );


  public static String getMessageByCode(String code) {
    return ERRORS.get(code);
  }
}
