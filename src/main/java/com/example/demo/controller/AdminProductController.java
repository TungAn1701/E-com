package com.example.demo.controller;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ProductRequest;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService productService;

    // 1. Thêm sản phẩm mới
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.addProduct(request));
    }

    // 2. Cập nhật giá và kho hàng (Update Inventory & Price)
    @PatchMapping()
    public ResponseEntity<Product> updateStockAndPrice(@RequestBody ProductRequest request){
        return ResponseEntity.ok(productService.updateProduct(request.getId(), request.getPrice(), request.getStock()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@RequestBody ProductRequest request){
        
        productService.deleteProduct(request.getId());
        return ResponseEntity.noContent().build();
    }
}