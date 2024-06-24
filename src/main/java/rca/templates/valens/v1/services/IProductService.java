package rca.templates.valens.v1.services;

import rca.templates.valens.v1.dtos.requests.CreateProductDTO;
import rca.templates.valens.v1.models.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IProductService {
    public List<Product> getAllProducts();

    public Optional<Product> getProductById(UUID id);

    public Product saveProduct(CreateProductDTO dto);

    public void deleteProduct(UUID id);
}
