package com.example.demo;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
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

// Static import để code gọn sạch
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setup() {
        productRepository.deleteAll();

      
        Product p1 = new Product();
        p1.setName("Laptop Gaming");
        p1.setPrice(BigDecimal.valueOf(2000.0));
        p1.setStock(10);
        productRepository.save(p1);

        Product p2 = new Product();
        p2.setName("iPhone 15");
        p2.setPrice(BigDecimal.valueOf(1000.0));
        p2.setStock(5);
        productRepository.save(p2);
    }

    
    @Test
    public void testGetAllProducts() throws Exception {
        mockMvc.perform(get("/api/products")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.totalElements").value(2));
    }

    // 2. Test tìm kiếm sản phẩm (Public)
    @Test
    public void testSearchProduct() throws Exception {
        mockMvc.perform(get("/api/products")
                .param("name", "iPhone"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("iPhone 15"));
    }

   
    @Test
    @WithMockUser(roles = "USER") 
    public void testAddProductAsUser_ShouldBeForbidden() throws Exception {
        String productJson = "{\"name\":\"Hacker Phone\", \"price\":999, \"stock\":1}";
        
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson))
                .andExpect(status().isForbidden()); 
    }

   
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAddProductAsAdmin_Success() throws Exception {
        String productJson = "{\"name\":\"Macbook M3\", \"price\":1500, \"stock\":20}";
        
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson))
                .andExpect(status().isOk());
    }
}