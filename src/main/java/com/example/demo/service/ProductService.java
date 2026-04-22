package com.example.demo.service;

import com.example.demo.dto.ProductRequest;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page; // PHẢI LÀ ĐƯỜNG DẪN NÀY
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

   
    public Page<Product> getProducts(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (name != null && !name.isEmpty()) {
            return productRepository.findByNameContainingIgnoreCase(name, pageable);
        }
        return productRepository.findAll(pageable);
    }

    
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm ID: " + id));
    }
    @Transactional
    public Product addProduct(ProductRequest request) {

    if (productRepository.existsByNameIgnoreCase(request.getName())) {
        throw new RuntimeException("Sản phẩm này đã tồn tại!");
    }

    Product product = new Product();
    product.setName(request.getName());
    product.setDescription(request.getDescription());
    product.setPrice(request.getPrice());
    product.setStock(request.getStock());

    return productRepository.save(product);
}
}