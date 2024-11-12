package bank.app.exeptions;

public class AccountIsBlockedException extends RuntimeException {
    public AccountIsBlockedException(String message) {
        super(message);
    }
}
