package rca.ne.java.v1.controllers;

import rca.ne.java.v1.exceptions.BadRequestException;
import rca.ne.java.v1.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashSet;
import java.util.Set;

/**
 * Global controller advice for handling exceptions across all controllers.
 */
@Component
public class Controller {

    /**
     * Exception handler for BadRequestException.
     * @param badRequestException The BadRequestException thrown.
     * @return ResponseEntity with a bad request status and error message.
     */
    @ExceptionHandler
    public ResponseEntity<ApiResponse> handleBadRequestException(BadRequestException badRequestException) {
        return ResponseEntity.badRequest().body(ApiResponse.fail(badRequestException.getMessage()));
    }

    /**
     * Exception handler for MethodArgumentNotValidException.
     * @param exception The MethodArgumentNotValidException thrown.
     * @return ResponseEntity with a bad request status and validation error messages.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Set<String> errorMessages = new HashSet<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            errorMessages.add(error.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, errorMessages));
    }
}
