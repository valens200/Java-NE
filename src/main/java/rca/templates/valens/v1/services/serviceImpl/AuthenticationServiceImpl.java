package rca.templates.valens.v1.services.serviceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import rca.templates.valens.v1.dtos.requests.LoginDTO;
import rca.templates.valens.v1.dtos.requests.ResetPasswordDTO;
import rca.templates.valens.v1.dtos.requests.UserTypesDTO;
import rca.templates.valens.v1.dtos.response.LoginResponseDTO;
import rca.templates.valens.v1.exceptions.BadRequestException;
import rca.templates.valens.v1.models.Role;
import rca.templates.valens.v1.models.User;
import rca.templates.valens.v1.models.enums.EAccountStatus;
import rca.templates.valens.v1.payload.ApiResponse;
import rca.templates.valens.v1.repositories.IUserRepository;
import rca.templates.valens.v1.security.User.UserSecurityDetails;
import rca.templates.valens.v1.security.User.UserSecurityDetailsService;
import rca.templates.valens.v1.security.jwt.JwtUtils;
import rca.templates.valens.v1.services.AuthenticationService;
import rca.templates.valens.v1.services.IFileService;
import rca.templates.valens.v1.services.IUserService;
import rca.templates.valens.v1.utils.ExceptionsUtils;
import rca.templates.valens.v1.utils.Hash;
import rca.templates.valens.v1.utils.SecurityUtils;
import rca.templates.valens.v1.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl extends ServiceImpl implements AuthenticationService {
    private final IUserRepository userRepository;
    private  final JwtUtils jwtUtils;
    private UserSecurityDetailsService securityDetailsService;
    private final MailServiceImpl mailService;
    private final IUserService userService;

    private final IFileService fileService;

    @Autowired
    public AuthenticationServiceImpl(IUserRepository userRepository, UserSecurityDetailsService userSecurityDetailsService, JwtUtils jwtUtils, MailServiceImpl mailService, IUserService userService, IFileService fileService) {
        this.userRepository = userRepository;
        this.securityDetailsService = userSecurityDetailsService;
        this.jwtUtils = jwtUtils;
        this.mailService = mailService;
        this.userService = userService;
        this.fileService = fileService;
    }

    @Override
    public LoginResponseDTO login(LoginDTO dto) {
        try {
            user = this.userRepository.findUserByEmail(dto.getEmail()).orElseThrow(()->new BadRequestException("Invalid email or password"));
            if(!SecurityUtils.isTheSameHash(dto.getPassword(),user.getPassword())) throw new BadRequestException("Invalid email or password");
            if(user.getStatus().name().equals(EAccountStatus.WAIT_EMAIL_VERIFICATION.name())) throw new BadRequestException("The account is nt yet activated");
            if(user.getStatus().name().equals(EAccountStatus.PENDING.name())) throw new BadRequestException("The account is still pending to be approved");
            userSecurityDetails = (UserSecurityDetails) securityDetailsService.loadUserByUsername(user.getEmail());
            authorities = userSecurityDetails.getGrantedAuthorities();

            List<String> roles = new ArrayList<>();
            for (GrantedAuthority grantedAuthority : authorities){
                roles.add(grantedAuthority.getAuthority());
            }

            String token = jwtUtils.createToken(user.getId(),user.getEmail(),roles);
            loginResponseDTO = new LoginResponseDTO(token,user);
            return loginResponseDTO;
        }catch (Exception exception){
            ExceptionsUtils.handleServiceExceptions(exception);
            return null;
        }
    }
    @Transactional
    private void assignTheRoles(User person , User user , Role role){
        UserTypesDTO userTypesDTO = new UserTypesDTO();
        userTypesDTO.setRole_id(role.getRoleId());
        userTypesDTO.setRole_name(role.getRoleName());
        userTypesDTO.setUser_id(person.getId());
        List<UserTypesDTO> userTypesDTOList = user.getUserTypesDTOList();
        if(userTypesDTOList == null) {
            userTypesDTOList = new ArrayList<>();
        }
        userTypesDTOList.add(userTypesDTO);
        user.setUserTypesDTOList(userTypesDTOList);
    }

    @Override
    public ResponseEntity<ApiResponse> verifyAccount(String email, String code) {
        return null;
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse> verifyResetCode(String email, String code) {
        try {
            Optional<User> optionalUser = userRepository.findUserByEmail(email);
            if(optionalUser.isEmpty()){
                return ResponseEntity.badRequest().body(new ApiResponse(
                        false,
                        "Code Verification failed"
                ));
            }else{
                User user = optionalUser.get();
                if(user.getActivationCode().equals(code)){
                    return ResponseEntity.ok().body(new ApiResponse(
                            true,
                            "Code Verification Complete"
                    ));
                }else{
                    return ResponseEntity.badRequest().body(new ApiResponse(
                            false,
                            "Wrong Verification Code"
                    ));
                }
            }
        }catch (Exception e){
            return ExceptionsUtils.handleControllerExceptions(e);
        }
    }


    @Override
    @Transactional
    public ResponseEntity<ApiResponse> resendVerificationCode(String email) {
        try {
            Optional<User> optionalUser = userRepository.findUserByEmail(email);
            if(optionalUser.isEmpty()){
                return ResponseEntity.badRequest().body(new ApiResponse(
                        false,
                        "Failed to resend the verification"
                ));
            }else{
                User user = optionalUser.get();
                mailService.sendAccountVerificationEmail(user);
                return ResponseEntity.ok().body(new ApiResponse(
                        true,
                        "Successfully Resent the verification",
                        user
                ));
            }
        }catch (Exception e){
            return ExceptionsUtils.handleControllerExceptions(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse> resetPassword(ResetPasswordDTO dto) {
        try {
            Optional<User> optionalUser = userRepository.findUserByEmail(dto.getEmail());
            if(optionalUser.isPresent()){
                User user = optionalUser.get();
                Hash hash = new Hash();
                String newPassword = hash.hashPassword(dto.getNewPassword());
                user.setPassword(newPassword);
                userRepository.save(user);
                return ResponseEntity.ok().body(new ApiResponse(
                        true,
                        "Password Reset Successfully",
                        user
                ));
            }else{
                return ResponseEntity.badRequest().body(new ApiResponse(
                        false,
                        "Reset Password Failed"
                ));
            }
        }catch (Exception e){
            return ExceptionsUtils.handleControllerExceptions(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse> initiatePasswordReset(String email) {
        try {
            User user = userRepository.findByEmail(email);
            if(user == null){
                return ResponseEntity.status(400).body(new ApiResponse(
                        false,
                        "Invalid EmailS"
                ));
            }else{
                String newActivationCode = Utility.getRandomUUID(6 , 0 , 'N');
                user.setActivationCode(newActivationCode);
                mailService.sendResetPasswordMail(user);
                return ResponseEntity.status(200).body(new ApiResponse(
                        true,
                        "Successfully initiated password reset",
                        user
                ));
            }
        }catch (Exception e){
            return ExceptionsUtils.handleControllerExceptions(e);
        }
    }

    public ResponseEntity<ApiResponse> changeProfile(MultipartFile file, UUID userId) throws IOException {
        try {
            User user = userRepository.findById(userId).orElseThrow(()->{
                throw new BadRequestException("User not found");
            });
            if(user == null){
                return ResponseEntity.status(400).body(new ApiResponse(
                        false,
                        "Invalid User"
                ));
            }else{
                String profilePic = fileService.uploadFile(file);
                user.setProfilePicture(profilePic);
                userRepository.save(user);
                return ResponseEntity.status(200).body(new ApiResponse(
                        true,
                        "Successfully changed the profile picture",
                        user
                ));
            }
        }catch (Exception e){
            return ExceptionsUtils.handleControllerExceptions(e);
        }
    }
}
