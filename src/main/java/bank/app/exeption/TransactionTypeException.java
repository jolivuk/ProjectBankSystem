package bank.app.exeption;

public class TransactionTypeException extends RuntimeException {
    public TransactionTypeException(String message) {
        super(message);
    }
}
