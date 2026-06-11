package com.naman.paymentsystem.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class AppControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> AppRuntimeException(RuntimeException ex, HttpServletRequest request) {

        Map<String, Object> response = new HashMap<>();

        response.put("Status", 400);
        response.put("message", ex.getLocalizedMessage());

        return ResponseEntity.badRequest().body(response);
    }
}
