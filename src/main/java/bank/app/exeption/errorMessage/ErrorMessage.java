package bank.app.exeption.errorMessage;

import bank.app.model.enums.Role;

public class ErrorMessage {

    public static final String USER_NOT_FOUND = "User not found ID: ";

    public static final String MANAGER_ID_NOT_FOUND = "Manager with ID not found";

    public static final String USERNAME_NOT_FOUND = "The username was not found";

    public static final String MANAGER_ID_HAS_INCORRECT_ROLE = "Manager with ID has incorrect role";

    public static final String ACCOUNT_NOT_FOUND = "Account not found ID: ";

    public static final String ACCOUNT_BANK_NOT_FOUND = "Account with role BANK not found";

    public static final String ACCOUNT_IS_DELETED = "Account is deleted, ID: ";

    public static final String ACCOUNT_IS_BLOCKED = "Account is blocked, ID: ";

    public static final String INVALID_TRANSATION_ID = "Invalid transaction ID";

    public static final String INVALID_TRANSATION_TYPE = "Invalid transaction Type";

    public static final String TRANSATION_NOT_FOUND = "Transaction not found ID: ";

    public static final String USER_WITH_THIS_ROLE_NOT_FOUND = "User with role not found";

    public static final String INVALID_EMAIL = "The email is invalid";

    public static final String INVALID_PASSWORD = "The password is invalid";

    public static final String INVALID_PHONE_NUMBER = "The phone number is invalid";

    public static final String INVALID_LASTNAME = "The lastname is invalid";

    public static final String INVALID_FIRST_NAME = "The first name is invalid";

    public static final String INVALID_USERNAME = "The username is invalid";

    public static final String INVALID_DATA = "The data is invalid";

    public static final String NO_ACCESS_RIGHTS = "There is no access rights";
}
