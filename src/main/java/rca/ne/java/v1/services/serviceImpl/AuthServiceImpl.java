package rca.ne.java.v1.services.serviceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import rca.ne.java.v1.dtos.requests.LoginDTO;
import rca.ne.java.v1.dtos.response.LoginResponseDTO;
import rca.ne.java.v1.exceptions.BadRequestException;
import rca.ne.java.v1.models.enums.EAccountStatus;
import rca.ne.java.v1.repositories.IUserRepository;
import rca.ne.java.v1.security.User.UserSecurityDetails;
import rca.ne.java.v1.security.User.UserSecurityDetailsService;
import rca.ne.java.v1.security.jwt.JwtUtils;
import rca.ne.java.v1.services.IAuthService;
import rca.ne.java.v1.utils.ExceptionsUtils;
import rca.ne.java.v1.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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

    @Service()
    @Transactional(rollbackFor = Exception.class)
    public static class FileServiceImpl {

        @Value("${upload.dir.prod")
        private String uploadDir;

        public String uploadFile(MultipartFile file) throws IOException {
            if (file.isEmpty()) {
                return null;
            }
            String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String newFileName = UUID.randomUUID() + fileExtension;
            Path uploadPath = Paths.get(uploadDir);

            if (!uploadPath.toFile().exists()) {
                uploadPath.toFile().mkdirs();
            }

            File destination = new File(uploadPath.toFile(), newFileName);
            file.transferTo(destination);

            return newFileName;
        }

        public String updateFile(String oldFileName, MultipartFile newFile) throws IOException {
            if (newFile.isEmpty()) {
                return oldFileName;
            }
            // Delete the old file
            if (oldFileName != null) {
                Path oldFilePath = Paths.get(uploadDir, oldFileName);
                File oldFile = oldFilePath.toFile();
                if (oldFile.exists()) {
                    oldFile.delete();
                }
            }

            return uploadFile(newFile);
        }

        public void removeFile(String fileName) {
            if (fileName != null) {
                Path filePath = Paths.get(uploadDir, fileName);
                File file = filePath.toFile();
                if (file.exists()) {
                    file.delete();
                }
            }
        }

        public File getFile(String fileName) {
            if (fileName != null) {
                Path filePath = Paths.get(uploadDir, fileName);
                File file = filePath.toFile();
                if (file.exists()) {
                    return file;
                }
            }
            return null;
        }
    }
}
