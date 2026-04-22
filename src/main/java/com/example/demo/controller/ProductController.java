package com.example.demo.controller;

import com.example.demo.dto.ProductRequest;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page; 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor 
public class ProductController {

    private final ProductService productService;

  
    @GetMapping
    public ResponseEntity<Page<Product>> getProducts(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        // Gọi Service để đảm bảo logic bảo mật và tìm kiếm được thực thi
        Page<Product> productPage = productService.getProducts(name, page, size);
        return ResponseEntity.ok(productPage);
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody ProductRequest request) {
    return ResponseEntity.ok(productService.addProduct(request));
    }
}