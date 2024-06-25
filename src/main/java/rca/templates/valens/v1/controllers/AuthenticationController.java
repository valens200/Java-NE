package rca.templates.valens.v1.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rca.templates.valens.v1.dtos.requests.LoginDTO;
import rca.templates.valens.v1.dtos.requests.ResetPasswordDTO;
import rca.templates.valens.v1.payload.ApiResponse;
import rca.templates.valens.v1.services.AuthenticationService;
import rca.templates.valens.v1.utils.ExceptionsUtils;

import java.io.IOException;
import java.util.UUID;

/**
 * Controller class handling authentication-related endpoints.
 */
@RestController
@RequestMapping("/api/v1/auth")
//@CrossOrigin("*") // Uncomment to enable CORS globally for this controller
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Endpoint for user login.
     * @param dto The login request DTO containing email and password.
     * @return ResponseEntity containing ApiResponse with login result.
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody() LoginDTO dto){
        try{
            log.info("Request for logging in with email : {} and password : {}", dto.getEmail(),dto.getPassword());
            return ResponseEntity.ok(new ApiResponse(true, "User logged in successfully", authenticationService.login(dto)));
        }catch (Exception exception){
            return ExceptionsUtils.handleControllerExceptions(exception);
        }
    }

    /**
     * Endpoint to verify user account.
     * @param email User's email address.
     * @param code Verification code sent to the user.
     * @return ResponseEntity containing ApiResponse indicating account verification status.
     */
    @GetMapping("/verify-account")
    public ResponseEntity<ApiResponse> verifyAccount(@RequestParam String email, @RequestParam String code){
        return authenticationService.verifyAccount(email, code);
    }

    /**
     * Endpoint to verify reset password code.
     * @param email User's email address.
     * @param code Reset password code.
     * @return ResponseEntity containing ApiResponse indicating reset code verification status.
     */
    @GetMapping("/verify-reset-code")
    public ResponseEntity<ApiResponse> verifyResetCode(@RequestParam String email, @RequestParam String code){
        return authenticationService.verifyResetCode(email, code);
    }

    /**
     * Endpoint to resend verification code.
     * @param email User's email address.
     * @return ResponseEntity containing ApiResponse indicating status of verification code resend operation.
     */
    @GetMapping("/resend-verification-code")
    public ResponseEntity<ApiResponse> resendVerificationCode(@RequestParam String email){
        return authenticationService.resendVerificationCode(email);
    }

    /**
     * Endpoint to reset user password.
     * @param dto ResetPasswordDTO containing email and new password.
     * @return ResponseEntity containing ApiResponse indicating password reset status.
     */
    @PutMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody() ResetPasswordDTO dto){
        return authenticationService.resetPassword(dto);
    }

    /**
     * Endpoint to initiate password reset process.
     * @param email User's email address.
     * @return ResponseEntity containing ApiResponse indicating initiation status of password reset.
     */
    @PostMapping("/initiate-reset-password")
    public ResponseEntity<ApiResponse> initiateResetPassword(@RequestParam() String email){
        log.info("Request for resetting password in with email : {}", email);
        return authenticationService.initiatePasswordReset(email);
    }

    /**
     * Placeholder endpoint for retrieving user profile.
     * @return ResponseEntity containing ApiResponse with user profile data.
     */
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse> getProfile(){
        return null; // To be implemented: authenticationService.getProfile();
    }

    /**
     * Placeholder endpoint for retrieving user profile by ID.
     * @param id User's unique ID.
     * @return ResponseEntity containing ApiResponse with user profile data.
     */
    @GetMapping("/profile/{id}")
    public ResponseEntity<ApiResponse> getProfileById(@PathVariable(value="id") UUID id) {
        return null; // To be implemented: authenticationService.getProfileById(id);
    }

    /**
     * Placeholder endpoint for changing user profile picture.
     * @param file Profile picture file to be uploaded.
     * @param userId User's unique ID.
     * @return ResponseEntity containing ApiResponse indicating status of profile picture change.
     * @throws IOException If there is an error handling the profile picture file.
     */
    @PatchMapping("/profile/{userId}")
    public ResponseEntity<ApiResponse> changeProfilePic(@RequestParam(name = "profile") MultipartFile file , @PathVariable(value = "userId") UUID userId) throws IOException {
        return null; // To be implemented: authenticationService.changeProfile(file, userId);
    }
}
