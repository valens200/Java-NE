package rca.ne.java.v1.services;
import rca.ne.java.v1.dtos.requests.LoginDTO;
import rca.ne.java.v1.dtos.requests.ResetPasswordDTO;
import rca.ne.java.v1.dtos.response.LoginResponseDTO;
import rca.ne.java.v1.payload.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    public LoginResponseDTO login(LoginDTO dto);
    ResponseEntity<ApiResponse> verifyAccount(String email, String code);

    ResponseEntity<ApiResponse> verifyResetCode(String email, String code);
    ResponseEntity<ApiResponse> resendVerificationCode(String email);
    ResponseEntity<ApiResponse> resetPassword(ResetPasswordDTO dto);
    ResponseEntity<ApiResponse> initiatePasswordReset(String email);
}
