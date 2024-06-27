package rca.ne.java.v1.services.serviceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import rca.ne.java.v1.services.AuthenticationService;
import rca.ne.java.v1.services.IFileService;
import rca.ne.java.v1.services.IUserService;
import rca.ne.java.v1.dtos.requests.LoginDTO;
import rca.ne.java.v1.dtos.requests.ResetPasswordDTO;
import rca.ne.java.v1.dtos.response.LoginResponseDTO;
import rca.ne.java.v1.exceptions.BadRequestException;
import rca.ne.java.v1.models.User;
import rca.ne.java.v1.models.enums.EAccountStatus;
import rca.ne.java.v1.payload.ApiResponse;
import rca.ne.java.v1.repositories.IUserRepository;
import rca.ne.java.v1.security.User.UserSecurityDetails;
import rca.ne.java.v1.security.User.UserSecurityDetailsService;
import rca.ne.java.v1.security.jwt.JwtUtils;
import rca.ne.java.v1.utils.ExceptionsUtils;
import rca.ne.java.v1.utils.SecurityUtils;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;

/**
 * Implementation of AuthenticationService providing authentication and account management functionality.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl extends ServiceImpl implements AuthenticationService {

    private final IUserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final UserSecurityDetailsService securityDetailsService;
    private final MailServiceImpl mailService;
    private final IUserService userService;
    private final IFileService fileService;

    @Autowired
    public AuthenticationServiceImpl(IUserRepository userRepository, UserSecurityDetailsService userSecurityDetailsService,
                                     JwtUtils jwtUtils, MailServiceImpl mailService, IUserService userService, IFileService fileService) {
        this.userRepository = userRepository;
        this.securityDetailsService = userSecurityDetailsService;
        this.jwtUtils = jwtUtils;
        this.mailService = mailService;
        this.userService = userService;
        this.fileService = fileService;
    }

    /**
     * Method to authenticate user login.
     * @param dto The LoginDTO containing user email and password.
     * @return LoginResponseDTO containing authentication token and user details upon successful login.
     */
    @Override
    public LoginResponseDTO login(LoginDTO dto) {
        try {
            User user = this.userRepository.findUserByEmail(dto.getEmail())
                    .orElseThrow(() -> new BadRequestException("Invalid email or password"));

            if (!SecurityUtils.isTheSameHash(dto.getPassword(), user.getPassword())) {
                throw new BadRequestException("Invalid email or password");
            }

            if (user.getStatus().name().equals(EAccountStatus.WAIT_EMAIL_VERIFICATION.name())) {
                throw new BadRequestException("The account is not yet activated");
            }

            if (user.getStatus().name().equals(EAccountStatus.PENDING.name())) {
                throw new BadRequestException("The account is still pending approval");
            }

            UserSecurityDetails userSecurityDetails = (UserSecurityDetails) securityDetailsService.loadUserByUsername(user.getEmail());
            System.out.println(userSecurityDetails.toString());
            Collection<? extends GrantedAuthority> authorities = userSecurityDetails.getGrantedAuthorities();

            List<String> roles = new ArrayList<>();
            for (GrantedAuthority grantedAuthority : authorities) {
                roles.add(grantedAuthority.getAuthority());
            }
            String token = jwtUtils.createToken(user.getId(), user.getEmail(), roles);
            return new LoginResponseDTO(token, user);
        } catch (Exception exception) {
            ExceptionsUtils.handleServiceExceptions(exception);
            return null;
        }
    }

    /**
     * Method to verify user account using email and verification code.
     * @param email User's email address.
     * @param code Verification code sent to the user.
     * @return ResponseEntity containing ApiResponse indicating account verification status.
     */
    @Override
    @Transactional
    public ResponseEntity<ApiResponse> verifyAccount(String email, String code) {
        // Implementation to be added
        return null;
    }

    /**
     * Method to verify reset password code.
     * @param email User's email address.
     * @param code Reset password code.
     * @return ResponseEntity containing ApiResponse indicating reset code verification status.
     */
    @Override
    @Transactional
    public ResponseEntity<ApiResponse> verifyResetCode(String email, String code) {
        // Implementation to be added
        return null;
    }

    /**
     * Method to resend verification code to a user's email address.
     * @param email User's email address.
     * @return ResponseEntity containing ApiResponse indicating status of verification code resend operation.
     */
    @Override
    @Transactional
    public ResponseEntity<ApiResponse> resendVerificationCode(String email) {
        // Implementation to be added
        return null;
    }

    /**
     * Method to reset user password based on ResetPasswordDTO.
     * @param dto ResetPasswordDTO containing user email and new password.
     * @return ResponseEntity containing ApiResponse indicating password reset status.
     */
    @Override
    @Transactional
    public ResponseEntity<ApiResponse> resetPassword(ResetPasswordDTO dto) {
        // Implementation to be added
        return null;
    }

    /**
     * Method to initiate password reset process for a user.
     * @param email User's email address.
     * @return ResponseEntity containing ApiResponse indicating initiation status of password reset.
     */
    @Override
    @Transactional
    public ResponseEntity<ApiResponse> initiatePasswordReset(String email) {
        // Implementation to be added
        return null;
    }

    /**
     * Method to change user profile picture.
     * @param file Profile picture file to be uploaded.
     * @param userId User's unique ID.
     * @return ResponseEntity containing ApiResponse indicating status of profile picture change.
     * @throws IOException If there is an error handling the profile picture file.
     */
    public ResponseEntity<ApiResponse> changeProfile(MultipartFile file, UUID userId) throws IOException {
        // Implementation to be added
        return null;
    }
}
