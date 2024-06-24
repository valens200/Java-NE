package rca.templates.valens.v1.controllers;
import rca.templates.valens.v1.dtos.requests.CreateProductDTO;
import rca.templates.valens.v1.exceptions.BadRequestException;
import rca.templates.valens.v1.models.Product;
import rca.templates.valens.v1.payload.ApiResponse;
import rca.templates.valens.v1.services.IProductService;
import rca.templates.valens.v1.utils.ExceptionsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.UnexpectedTypeException;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static rca.templates.valens.v1.utils.helpers.Helper.logAction;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController{

    private final IProductService productService;


    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        try {
            return ResponseEntity.ok(new ApiResponse(true,"All products were retrieved successfully", productService.getAllProducts()));

        }catch (Exception exception){
            return ExceptionsUtils.handleControllerExceptions(exception);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable UUID id) {
        try{
            logAction(String.format("Request for getting a product by ID : %s", id));
            Optional<Product> product = productService.getProductById(id);

            return ResponseEntity.ok(new ApiResponse(true, "Product was retrieved successfully", product));
        }catch (Exception exception){
            return ExceptionsUtils.handleControllerExceptions(exception);
        }

    }

    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> createProduct(@Valid @RequestBody  CreateProductDTO dto) {
        try {
            Product createdProduct = productService.saveProduct(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(true, "The product was created successfully", createdProduct));
        }catch (Exception exception){
           return ExceptionsUtils.handleControllerExceptions(exception);
        }
    }
    @ExceptionHandler
    public ResponseEntity<ApiResponse> handleBadRequestException(BadRequestException badRequestException) {
        return ResponseEntity.badRequest().body(ApiResponse.fail(badRequestException.getMessage()));
    }
    @ExceptionHandler({MethodArgumentNotValidException.class, UnexpectedTypeException.class})
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Set<String> errorMessages = new HashSet<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            errorMessages.add(error.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false,errorMessages));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        try{
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        }catch (Exception exception){
            ExceptionsUtils.handleServiceExceptions(exception);
            return null;
        }
    }
}
