package com.templates.valens.v1.services.serviceImpl;
import com.templates.valens.v1.models.Product;
import com.templates.valens.v1.repositories.IProductRepository;
import com.templates.valens.v1.services.IProductService;
import com.templates.valens.v1.utils.ExceptionsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl  implements IProductService {

    private final IProductRepository productRepository;
    public List<Product> getAllProducts() {
        try {
            return productRepository.findAll();
        }catch (Exception exception){
            ExceptionsUtils.handleServiceExceptions(exception);
            return null;
        }
    }

    public Page<Product> getAllPaginatedProducts(Pageable pageable) {
        try {
            return productRepository.findAll(pageable);
        }catch (Exception exception){
            ExceptionsUtils.handleServiceExceptions(exception);
            return null;
        }
    }

    public Optional<Product> getProductById(UUID id) {
        try{
            return productRepository.findById(id);
        }catch (Exception exception){
            ExceptionsUtils.handleServiceExceptions(exception);
            return null;
        }
    }

    public Product saveProduct(Product product) {
        try {
            return productRepository.save(product);
        }catch (Exception exception){
            ExceptionsUtils.handleServiceExceptions(exception);
            return null;
        }
    }

    public void deleteProduct(UUID id) {
        try{
            productRepository.deleteById(id);
        }catch (Exception exception){
            ExceptionsUtils.handleServiceExceptions(exception);
        }
    }
}
