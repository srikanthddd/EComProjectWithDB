package com.myproject.ecommerce.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvices {

    @ExceptionHandler(ProductNotFoundException.class)
    private ResponseEntity<ErrorDTO> handleProductNotFoundException(ProductNotFoundException productNotFoundException){
        ErrorDTO error = new ErrorDTO(1234, productNotFoundException.getMessage());
        ResponseEntity<ErrorDTO> responseEntity = new ResponseEntity<>(
                error,
                HttpStatus.NOT_FOUND
        );
        return responseEntity;
    }

    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    private ResponseEntity<ErrorDTO> handleArrayIndexOutOfBoundsException(ArrayIndexOutOfBoundsException productNotFoundException){
        ErrorDTO error = new ErrorDTO(1234, "Array index outof bound");
        ResponseEntity<ErrorDTO> responseEntity = new ResponseEntity<>(
                error,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        return responseEntity;
    }

}
