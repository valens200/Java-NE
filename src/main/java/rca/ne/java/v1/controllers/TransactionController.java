package rca.ne.java.v1.controllers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rca.ne.java.v1.dtos.requests.CreateTransactionDTO;
import rca.ne.java.v1.dtos.requests.SaveOrWithDrawDTO;
import rca.ne.java.v1.models.enums.EBankingType;
import rca.ne.java.v1.payload.ApiResponse;
import rca.ne.java.v1.services.ITransactionService;
import rca.ne.java.v1.utils.ExceptionsUtils;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController extends Controller {
private final ITransactionService transactionService;

    /**
     *
     * @param dto
     * @param bankingType
     * @return
     */
    @PostMapping("transfer")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<ApiResponse> transfer(@Valid  @RequestBody CreateTransactionDTO dto, @RequestParam("bankingType")EBankingType bankingType){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(true,"The transaction was created successfully", this.transactionService.transfer(dto,bankingType)));
        }catch (Exception exception){
            return ExceptionsUtils.handleControllerExceptions(exception);
        }
    }

    /**
     *
     * @param dto
     * @return
     */
    @PutMapping("/save")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody  SaveOrWithDrawDTO dto){
        try {
            return ResponseEntity.ok(new ApiResponse(true, "Saving was successful", this.transactionService.save(dto)));
        }catch (Exception exception){
            System.out.println("=====================================================================");
            System.out.println(exception.getMessage());
            System.out.println("=====================================================================");
            return ExceptionsUtils.handleControllerExceptions(exception);
        }
    }

    /**
     *
     * @param dto
     * @return
     */
    @PutMapping("/withdraw")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<ApiResponse> withdraw(@Valid @RequestBody SaveOrWithDrawDTO dto){
        try {
            return ResponseEntity.ok(new ApiResponse(true, "Withdrawing was successful", this.transactionService.withdraw(dto)));
        }catch (Exception exception){
            return ExceptionsUtils.handleControllerExceptions(exception);
        }
    }
    /**
     *
     * @param Re
     * @return
     */
    @GetMapping("/my_account")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<ApiResponse> getMyAccount(){
        try{
            return ResponseEntity.ok(new ApiResponse(true,"Your account was retrieved successfully", this.transactionService.getMyAccount()));
        }catch (Exception exception){
           return ExceptionsUtils.handleControllerExceptions(exception);
        }
    }
}
