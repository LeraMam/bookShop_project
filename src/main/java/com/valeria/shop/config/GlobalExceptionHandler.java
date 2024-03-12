package com.valeria.shop.config;

import com.valeria.shop.dto.ErrorDTO;
import com.valeria.shop.exception.ApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiException.class)
    ResponseEntity<ErrorDTO> handle(ApiException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(new ErrorDTO(e.getMessage()));
    }
}
