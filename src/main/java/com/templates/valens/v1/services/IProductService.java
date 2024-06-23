package com.templates.valens.v1.services;

import com.templates.valens.v1.models.Product;
import com.templates.valens.v1.repositories.IProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IProductService {
    public List<Product> getAllProducts();

    public Optional<Product> getProductById(UUID id);

    public Product saveProduct(Product product);

    public void deleteProduct(UUID id);
}
