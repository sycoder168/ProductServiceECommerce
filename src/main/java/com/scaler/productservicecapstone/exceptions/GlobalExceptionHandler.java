package com.scaler.productservicecapstone.exceptions;

import com.scaler.productservicecapstone.dtos.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public ErrorDto handleNullPointerException() {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage("NPE occurred");
        errorDto.setStatus("Failure");
        return errorDto;
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDto> handleProductNotFoundException(Exception e) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(e.getMessage());
        errorDto.setStatus("Failure");
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }
}
