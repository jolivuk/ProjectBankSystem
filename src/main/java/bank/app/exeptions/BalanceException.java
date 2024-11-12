package bank.app.exeptions;

public class BalanceException extends RuntimeException {
    public BalanceException(String message) {
        super(message);
    }
}
