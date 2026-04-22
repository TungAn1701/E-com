package com.example.demo.repository;


import com.example.demo.entity.Product;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
   Optional<Product> findByProductId(Long productId);
   Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
   List<Product> findByDescriptionContainingIgnoreCase(String description);
   boolean existsByNameIgnoreCase(String name);
}