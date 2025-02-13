package org.example.bankapp.errorHandeling;

import org.example.bankapp.model.ResponseMassage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorControllerHanling {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ResponseMassage> NotAuthorized(SecurityException ex) {
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ResponseMassage(ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseMassage> handleAllExceptions(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMassage(ex.getMessage()));
    }
}
