package com.example.demo.service;

import com.example.demo.entity.CartItem;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartItem addToCart(Long productId, int quantity) {
        // 1. Lấy username của người đang đăng nhập từ Token
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

        
        return cartItemRepository.findByUserAndProduct_ProductId(user, productId)
                .map(item -> {
                    item.setQuantity(item.getQuantity() + quantity);
                    return cartItemRepository.save(item);
                })
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setUser(user);
                    newItem.setProduct(product);
                    newItem.setQuantity(quantity);
                    return cartItemRepository.save(newItem);
                });
    }
    @Transactional
public void removeFromCart(Long productId) {
    
    String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userRepository.findByUsername(currentUsername)
            .orElseThrow(() -> new RuntimeException("User không tồn tại"));

    
    CartItem item = cartItemRepository.findByUserAndProduct_ProductId(user, productId)
            .orElseThrow(() -> new RuntimeException("Sản phẩm này không có trong giỏ hàng của bạn!"));

    
    cartItemRepository.delete(item);
}
    public List<CartItem> getMyCart() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));
        return cartItemRepository.findByUser(user);
    }
}