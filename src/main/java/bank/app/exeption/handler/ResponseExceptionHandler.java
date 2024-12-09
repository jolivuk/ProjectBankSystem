package bank.app.exeption.handler;

import bank.app.exeption.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static bank.app.exeption.errorMessage.ErrorMessage.*;

@RestControllerAdvice
public class ResponseExceptionHandler {

    @ExceptionHandler(UserAlreadyDeletedException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyDeleted(UserAlreadyDeletedException exception) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserRoleException.class)
    public ResponseEntity<ErrorResponse> handleUserRoleException(UserRoleException exception) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.EXPECTATION_FAILED.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.EXPECTATION_FAILED);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException exception) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFoundException(AccountNotFoundException exception) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccountIsBlockedException.class)
    public ResponseEntity<ErrorResponse> handleAccountIsBlockedException(AccountIsBlockedException exception) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.LOCKED.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.LOCKED);
    }

    @ExceptionHandler(AccountAlreadyDeletedException.class)
    public ResponseEntity<ErrorResponse> handleAccountAlreadyDeletedException(AccountAlreadyDeletedException exception) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BalanceException.class)
    public ResponseEntity<ErrorResponse> handleBalanceException(BalanceException exception) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.PAYMENT_REQUIRED.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.PAYMENT_REQUIRED);
    }

    @ExceptionHandler(TransactionTypeException.class)
    public ResponseEntity<ErrorResponse> handleTransactionTypeException(TransactionTypeException exception) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StartDateAfterEndDateException.class)
    public ResponseEntity<ErrorResponse> handleStartDateAfterEndDateException(StartDateAfterEndDateException exception) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTransactionNotFoundException(TransactionNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDuplicateEntryException(DataIntegrityViolationException ex) {

        if (ex.getCause() instanceof SQLException sqlException) {
            if (sqlException.getErrorCode() == 1062) {
                return new ResponseEntity<>(USER_ALREADY_EXISTS, HttpStatus.CONFLICT);
            }
        }
        return new ResponseEntity<>(DATABASE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
