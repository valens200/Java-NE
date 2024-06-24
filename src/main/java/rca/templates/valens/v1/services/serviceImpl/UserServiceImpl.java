package rca.templates.valens.v1.services.serviceImpl;
import rca.templates.valens.v1.dtos.requests.CreateUserDTO;
import rca.templates.valens.v1.exceptions.BadRequestException;
import rca.templates.valens.v1.exceptions.NotFoundException;
import rca.templates.valens.v1.exceptions.UnauthorizedException;
import rca.templates.valens.v1.models.User;
import rca.templates.valens.v1.repositories.IUserRepository;
import rca.templates.valens.v1.security.User.UserSecurityDetails;
import rca.templates.valens.v1.services.IRoleService;
import rca.templates.valens.v1.services.IUserService;
import rca.templates.valens.v1.utils.ExceptionsUtils;
import rca.templates.valens.v1.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl implements IUserService {

    private final IUserRepository userRepository;
    private  final IRoleService roleService;
    private final PasswordEncoder passwordEncoder;
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(UUID id) {
        try {
            return userRepository.findById(id).orElseThrow(() ->new NotFoundException("The user with the provided id doesn't exist"));
        }catch (Exception exception){
            ExceptionsUtils.handleServiceExceptions(exception);
            return null;
        }
    }

    @Override
    public User createAdmin(CreateUserDTO dto) {
        try {
            if(userRepository.existsByEmail(dto.getEmail())) throw new BadRequestException("The user with the provided email already exists");
            user = Mapper.getUserFromDTO(dto);
            User user = Mapper.getUserFromDTO(dto);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            role = roleService.getByRoleName("ADMIN");
            roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
            return userRepository.save(user);
        }catch (Exception exception){
            ExceptionsUtils.handleServiceExceptions(exception);
            return null;
        }
    }

    @Override
    public User updateUser(UUID id, User user) {
        // Check if the user with given id exists
        if (userRepository.existsById(id)) {
            user.setId(id); // Ensure the correct ID is set for update
            return userRepository.save(user);
        }
        return null; // Return null or throw exception based on your business logic
    }

    public  boolean isUserLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal().equals("anonymousUser")) {
            return false;
        } else {
            return true;
        }
    }

    public User getLoggedInUser() {
        UserSecurityDetails userSecurityDetails;
        // Retrieve the currently authenticated user from the SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            System.out.println("here");
            System.out.println(authentication.getPrincipal() );
            userSecurityDetails = (UserSecurityDetails) authentication.getPrincipal();
            return this.userRepository.findUserByEmail(userSecurityDetails.getUsername()).orElseThrow(() -> new UnauthorizedException("You are not authorized! please login"));
        } else {
            throw new UnauthorizedException("You are not authorized! please login");
        }
    }
    public boolean isNotUnique(User user) {
        return this.userRepository.findUserByEmail(user.getEmail()).isPresent();
    }

    public boolean validateUserEntry(User user) {
        if (isNotUnique(user)) {
            String errorMessage = "The user with the email: " + user.getEmail() +
                    "  or national id: " + user.getEmail() +
                    " or name : " + user.getFirstName() + " " + user.getLastName() +
                    " already exists";
            throw new BadRequestException(errorMessage);
        } else {
            return true;
        }
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
