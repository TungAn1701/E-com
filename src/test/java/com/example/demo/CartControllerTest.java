package com.example.demo;

import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional // Tự động xóa dữ liệu sau mỗi hàm test
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    private Long savedProductId;

    @BeforeEach
    void setup() {
        cartItemRepository.deleteAll();
        productRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setUsername("an_tung");
        user.setPassword("password");
        userRepository.save(user);

        Product product = new Product();
        product.setName("iPhone 15");
        product.setPrice(BigDecimal.valueOf(1000.0));
        product.setStock(10);
        Product savedProduct = productRepository.save(product);

        this.savedProductId = savedProduct.getProductId();
    }

    @Test
    @WithMockUser(username = "an_tung")
    public void testAddToCart() throws Exception {
        String cartJson = String.format("{\"productId\":%d, \"quantity\":2}", savedProductId);
        
        mockMvc.perform(post("/api/cart/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cartJson))
                .andExpect(status().isOk());
    }
}