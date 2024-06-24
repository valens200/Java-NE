package rca.templates.valens.v1.controllers;

import rca.templates.valens.v1.dtos.requests.CreateUserDTO;
import rca.templates.valens.v1.payload.ApiResponse;
import rca.templates.valens.v1.services.IUserService;
import rca.templates.valens.v1.utils.ExceptionsUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller class for handling user-related operations.
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController extends Controller {

    private final IUserService userService;

    /**
     * Endpoint for creating an admin user.
     * @param userDTO The CreateUserDTO object containing admin user details.
     * @return ResponseEntity containing ApiResponse indicating success or failure of admin creation.
     */
    @PostMapping("/admin/create")
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody CreateUserDTO userDTO){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true, "Admin created successfully", userService.createAdmin(userDTO)));
        } catch (Exception exception) {
            return ExceptionsUtils.handleControllerExceptions(exception); // Handle exceptions using utility class
        }
    }
}
