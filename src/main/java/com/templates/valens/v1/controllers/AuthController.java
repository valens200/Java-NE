package com.templates.valens.v1.controllers;
import com.templates.valens.v1.dtos.requests.LoginDTO;
import com.templates.valens.v1.payload.ApiResponse;
import com.templates.valens.v1.services.IAuthService;
import com.templates.valens.v1.utils.ExceptionsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IAuthService authService;
    @PostMapping("login")
    public ResponseEntity<ApiResponse> login(@RequestBody() LoginDTO loginDTO){
        try {
            return ResponseEntity.ok(new ApiResponse(true,"The user logged in successfully", this.authService.login(loginDTO)));
        }catch (Exception exception){
            return ExceptionsUtils.handleControllerExceptions(exception);
        }
    }
}
