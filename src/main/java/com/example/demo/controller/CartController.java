package com.example.demo.controller;

import com.example.demo.dto.CartRequest;
import com.example.demo.entity.CartItem;
import com.example.demo.service.CartService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;


@PostMapping("/add")
public ResponseEntity<CartItem> addToCart(@RequestBody CartRequest request) {
    CartItem item = cartService.addToCart(request.getProductId(), request.getQuantity());
    return ResponseEntity.ok(item);
}
@DeleteMapping("/remove/{productId}")
public ResponseEntity<String> removeFromCart(@PathVariable Long productId) {
    // Cứ gọi thẳng, nếu có lỗi thì GlobalExceptionHandler sẽ tự hốt
    cartService.removeFromCart(productId);
    return ResponseEntity.ok("Xóa thành công!");
}
    
    @GetMapping
    public List<CartItem> getCart() {
        return cartService.getMyCart();
    }
}