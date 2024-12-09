package bank.app.exeption;

public class StartDateAfterEndDateException extends RuntimeException {

    public StartDateAfterEndDateException(String message) {
        super(message);
    }
}
