package com.templates.valens.v1.repositories;

import com.templates.valens.v1.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IProductRepository extends JpaRepository<Product, UUID> {
}
