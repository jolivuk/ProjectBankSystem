package bank.app.exeptions;

import java.util.concurrent.ExecutionException;

public class UserAlreadyDeletedException extends Exception {
    public UserAlreadyDeletedException(String message) {
        super(message);
    }
}
