package com.yata.backend.global.advice;


import com.yata.backend.global.exception.CustomLogicException;
import com.yata.backend.global.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionAdvice {
   @ExceptionHandler
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   public ErrorResponse handleMethodArgumentNotValidException(
           MethodArgumentNotValidException e) {

      return ErrorResponse.of(e.getBindingResult());
   }

   @ExceptionHandler
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   public ErrorResponse handleConstraintViolationException(
           ConstraintViolationException e) {
      return ErrorResponse.of(e.getConstraintViolations());
   }
   @ExceptionHandler
   public ResponseEntity<ErrorResponse> handleCustomException(CustomLogicException e) {
      return new ResponseEntity<>(ErrorResponse.of(e), HttpStatus.valueOf(e.getExceptionCode().getCode()));
   }
}
