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
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
//@CrossOrigin("*")
@Slf4j
public class AuthenticationController{
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody() LoginDTO dto){
        log.info("Request for logging in with email : {} and password : {}", dto.getEmail(),dto.getPassword());
        return ResponseEntity.ok(new ApiResponse(true, "The user loggedIn successfully",authenticationService.login(dto)));
    }
    @GetMapping("/verify-account")
    public ResponseEntity<ApiResponse> verifyAccount(@RequestParam String email,@RequestParam String code){

        return authenticationService.verifyAccount(email,code);

    }
    @GetMapping("/verify-reset-code")
    public ResponseEntity<ApiResponse> verifyResetCode(@RequestParam String email,@RequestParam String code){

        return authenticationService.verifyResetCode(email,code);

    }

    @GetMapping("/resend-verification-code")
    public ResponseEntity<ApiResponse> resendVerificationCode(@RequestParam String email){

        return authenticationService.resendVerificationCode(email);

    }
    @PutMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody() ResetPasswordDTO dto){
        return authenticationService.resetPassword(dto);

    }

    @PostMapping("/initiate-reset-password")
    public ResponseEntity<ApiResponse> initiateResetPassword(@RequestParam() String email){
        log.info("Request for resetting password in with email : {}", email);
        return authenticationService.initiatePasswordReset(email);
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse> getProfile(){
        return null;
//        return authenticationService.getProfile();
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<ApiResponse> getProfileById(@PathVariable(value="id") UUID id) {
        return null;
//        return authenticationService.getProfileById(id);
    }
    @PatchMapping("/profile/{userId}")
    public ResponseEntity<ApiResponse> changeProfilePic(@RequestParam(name = "profile") MultipartFile file , @PathVariable(value = "userId") UUID userId) throws IOException {
        return null;
//        return authenticationService.changeProfile(file,userId);
    }
}
