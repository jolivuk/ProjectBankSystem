package bank.app.controllers.handler;

import bank.app.exeptions.UserAlreadyDeletedException;
import bank.app.exeptions.UserNotFountException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ResponseExceptionHandler {
    @ExceptionHandler(UserNotFountException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFountException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(UserAlreadyDeletedException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyDeleted(UserAlreadyDeletedException exception){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
