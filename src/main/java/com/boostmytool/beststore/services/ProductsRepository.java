package com.boostmytool.beststore.services;

import com.boostmytool.beststore.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductsRepository  extends JpaRepository<Product, Integer> {
    List<Product> findByNameContainingIgnoreCaseOrBrandContainingIgnoreCaseOrCategoryContainingIgnoreCase(String name, String brand, String category);
}
