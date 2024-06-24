package rca.templates.valens.v1.services.serviceImpl;
import rca.templates.valens.v1.dtos.requests.LoginDTO;
import rca.templates.valens.v1.dtos.response.LoginResponseDTO;
import rca.templates.valens.v1.exceptions.BadRequestException;
import rca.templates.valens.v1.models.enums.EAccountStatus;
import rca.templates.valens.v1.repositories.IUserRepository;
import rca.templates.valens.v1.security.User.UserSecurityDetails;
import rca.templates.valens.v1.security.User.UserSecurityDetailsService;
import rca.templates.valens.v1.security.jwt.JwtUtils;
import rca.templates.valens.v1.services.IAuthService;
import rca.templates.valens.v1.utils.ExceptionsUtils;
import rca.templates.valens.v1.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl extends ServiceImpl implements IAuthService {
    private final IUserRepository userRepository;
    private final UserSecurityDetailsService securityDetailsService;
    private  final JwtUtils jwtUtils;

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
}
