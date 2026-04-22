package com.example.demo.repository;

import com.example.demo.entity.CartItem;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    
    @Transactional
    void deleteByUserAndProduct_ProductId(User user, Long productId);
    List<CartItem> findByUser(User user);
    
    Optional<CartItem> findByUserAndProduct_ProductId(User user, Long productId);
}