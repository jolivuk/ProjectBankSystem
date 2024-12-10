package bank.app.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.LOCKED)
public class UserAlreadyBlockedException extends RuntimeException {
    public UserAlreadyBlockedException(String message) {
        super(message);
    }
}
