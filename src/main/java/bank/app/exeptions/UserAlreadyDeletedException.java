package bank.app.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.concurrent.ExecutionException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserAlreadyDeletedException extends Exception {
    public UserAlreadyDeletedException(String message) {
        super(message);
    }
}
