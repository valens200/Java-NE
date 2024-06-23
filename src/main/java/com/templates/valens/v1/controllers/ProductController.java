package com.templates.valens.v1.controllers;
import com.templates.valens.v1.dtos.requests.CreateProductDTO;
import com.templates.valens.v1.models.Product;
import com.templates.valens.v1.payload.ApiResponse;
import com.templates.valens.v1.services.IProductService;
import com.templates.valens.v1.utils.ExceptionsUtils;
import com.templates.valens.v1.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

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
            Optional<Product> product = productService.getProductById(id);
            return ResponseEntity.ok(new ApiResponse(true, "Product was retrieved successfully", product));
        }catch (Exception exception){
            return ExceptionsUtils.handleControllerExceptions(exception);
        }

    }

    @PostMapping("create")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductDTO dto) {
        try {
            Product productEntity = Mapper.getProductFromDTO(dto);
            Product createdProduct = productService.saveProduct(productEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        }catch (Exception exception){
            ExceptionsUtils.handleServiceExceptions(exception);
            return null;
        }
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
