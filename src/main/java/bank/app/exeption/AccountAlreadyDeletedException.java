package bank.app.exeption;

public class AccountAlreadyDeletedException extends RuntimeException {

    public AccountAlreadyDeletedException(String message) {
        super(message);
    }
}
