package bank.app.exeptions;

public class UserRoleException extends RuntimeException {
    public UserRoleException(String message) {
        super(message);
    }
}
