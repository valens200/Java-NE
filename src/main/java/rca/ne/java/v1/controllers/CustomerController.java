package rca.ne.java.v1.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rca.ne.java.v1.dtos.requests.CreateCustomerDTO;
import rca.ne.java.v1.payload.ApiResponse;
import rca.ne.java.v1.services.ICustomerService;
import rca.ne.java.v1.utils.ExceptionsUtils;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/customers")
@Slf4j
@RequiredArgsConstructor
public class CustomerController extends Controller {
    private final ICustomerService customerService;

    /**
     * CustomerController::create
     * - Receives the dto with customer details and create new customer.
     * @param dto
     * @return
     */
    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody CreateCustomerDTO dto){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(true, "The customer was created successfully", this.customerService.create(dto)));
        }catch (Exception exception){
            return ExceptionsUtils.handleControllerExceptions(exception);
        }
    }
}
