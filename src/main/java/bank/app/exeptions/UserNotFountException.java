package bank.app.exeptions;

public class UserNotFountException extends RuntimeException{
    public UserNotFountException(String message) {
        super(message);
    }
}
