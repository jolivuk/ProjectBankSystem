package bank.app.controllers.handler;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String errorMessage = "Data integrity violation";
        String errorCode = "";

        Throwable rootCause = ex.getRootCause();
        if (rootCause instanceof SQLException) {
            SQLException sqlEx = (SQLException) rootCause;
            errorCode = String.valueOf(sqlEx.getErrorCode());
            errorMessage += ": " + sqlEx.getMessage();
            errorMessage += ": SQL error code " + errorCode;
            if (sqlEx.getErrorCode() == 1062) {
                return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
            }
        }

        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }
}
