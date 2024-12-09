package bank.app.exeption;

public class AccountIsBlockedException extends RuntimeException {
    public AccountIsBlockedException(String message) {
        super(message);
    }
}
