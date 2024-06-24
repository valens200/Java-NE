package rca.templates.valens.v1.services.serviceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of IUserService providing user management functionality.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl implements IUserService {

    private final IUserRepository userRepository;
    private final IRoleService roleService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Retrieves all users from the repository.
     * @return List of all users.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a user by their ID.
     * @param id The ID of the user to retrieve.
     * @return The User object if found, otherwise throws NotFoundException.
     */
    @Override
    public User getUserById(UUID id) {
        try {
            return userRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("The user with the provided id doesn't exist"));
        } catch (Exception exception) {
            ExceptionsUtils.handleServiceExceptions(exception);
            return null;
        }
    }

    /**
     * Creates a new admin user based on the provided CreateUserDTO.
     * @param dto The CreateUserDTO containing admin user details.
     * @return The created User object if successful, otherwise handles exceptions and returns null.
     */
    @Override
    public User createAdmin(CreateUserDTO dto) {
        try {
            if (userRepository.existsByEmail(dto.getEmail())) {
                throw new BadRequestException("The user with the provided email already exists");
            }

            User user = Mapper.getUserFromDTO(dto);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Role role = roleService.getByRoleName("ADMIN");
            user.setRoles(new HashSet<>(List.of(role)));
            return userRepository.save(user);
        } catch (Exception exception) {
            ExceptionsUtils.handleServiceExceptions(exception);
            return null;
        }
    }

    /**
     * Updates an existing user by their ID.
     * @param id The ID of the user to update.
     * @param user The updated User object.
     * @return The updated User object if successful, otherwise null.
     */
    @Override
    public User updateUser(UUID id, User user) {
        if (userRepository.existsById(id)) {
            user.setId(id); // Ensure the correct ID is set for update
            return userRepository.save(user);
        }
        return null;
    }

    /**
     * Checks if a user is currently logged in.
     * @return True if a user is logged in, false otherwise.
     */
    public boolean isUserLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && !authentication.getPrincipal().equals("anonymousUser");
    }

    /**
     * Retrieves the currently logged-in user.
     * @return The User object representing the logged-in user.
     * @throws UnauthorizedException If the user is not authenticated.
     */
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication.getPrincipal() instanceof String)) {
            UserSecurityDetails userSecurityDetails = (UserSecurityDetails) authentication.getPrincipal();
            return userRepository.findUserByEmail(userSecurityDetails.getUsername())
                    .orElseThrow(() -> new UnauthorizedException("You are not authorized! Please log in"));
        } else {
            throw new UnauthorizedException("You are not authorized! Please log in");
        }
    }

    /**
     * Checks if a user with the same email already exists.
     * @param user The User object to check.
     * @return True if a user with the same email exists, false otherwise.
     */
    public boolean isNotUnique(User user) {
        return userRepository.findUserByEmail(user.getEmail()).isPresent();
    }

    /**
     * Validates user entry to ensure uniqueness of email and national ID.
     * @param user The User object to validate.
     * @return True if the user entry is valid, otherwise throws BadRequestException.
     */
    public boolean validateUserEntry(User user) {
        if (isNotUnique(user)) {
            String errorMessage = "The user with the email: " + user.getEmail() +
                    " or national id: " + user.getEmail() +
                    " or name: " + user.getFirstName() + " " + user.getLastName() +
                    " already exists";
            throw new BadRequestException(errorMessage);
        } else {
            return true;
        }
    }

    /**
     * Deletes a user by their ID.
     * @param id The ID of the user to delete.
     */
    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
