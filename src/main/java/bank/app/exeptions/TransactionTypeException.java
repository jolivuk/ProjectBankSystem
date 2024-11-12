package bank.app.exeptions;

public class TransactionTypeException extends RuntimeException {
    public TransactionTypeException(String message) {
        super(message);
    }
}
