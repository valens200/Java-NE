package com.templates.valens.v1.controllers;

import com.templates.valens.v1.dtos.requests.CreateUserDTO;
import com.templates.valens.v1.exceptions.BadRequestException;
import com.templates.valens.v1.models.User;
import com.templates.valens.v1.payload.ApiResponse;
import com.templates.valens.v1.utils.ExceptionsUtils;
import com.templates.valens.v1.utils.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @PostMapping("create")
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody CreateUserDTO userDTO){
      try{
          User user = Mapper.getUserFromDTO(userDTO);
          return null;
      }catch (Exception exception){
          return ExceptionsUtils.handleControllerExceptions(exception);
      }
    }
    @ExceptionHandler
    public ResponseEntity<ApiResponse> handleBadRequestException(BadRequestException badRequestException) {
        return ResponseEntity.badRequest().body(ApiResponse.fail(badRequestException.getMessage()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        var errors = new HashMap<String, String>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            var fieldName = ((FieldError) error).getField();
            var errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
