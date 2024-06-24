package rca.templates.valens.v1.controllers;

import rca.templates.valens.v1.exceptions.BadRequestException;
import rca.templates.valens.v1.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashSet;
import java.util.Set;

@Component
public class Controller {
    @ExceptionHandler
    public ResponseEntity<ApiResponse> handleBadRequestException(BadRequestException badRequestException) {
        return ResponseEntity.badRequest().body(ApiResponse.fail(badRequestException.getMessage()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Set<String> errorMessages = new HashSet<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            errorMessages.add(error.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false,errorMessages));
    }
}
