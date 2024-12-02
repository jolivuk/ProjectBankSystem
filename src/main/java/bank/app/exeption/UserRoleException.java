package bank.app.exeption;

public class UserRoleException extends RuntimeException {
    public UserRoleException(String message) {
        super(message);
    }
}
