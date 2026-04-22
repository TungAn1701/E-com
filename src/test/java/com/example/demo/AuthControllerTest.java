package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
@Transactional // THÊM DÒNG NÀY: Nó sẽ tự động Rollback (xóa dữ liệu test) sau khi chạy xong mỗi hàm
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository.deleteAll(); // Đảm bảo database luôn sạch trước khi test
    }

    @Test
    public void testRegisterSuccess() throws Exception {
        String userJson = "{\"username\":\"tung_moi\",\"password\":\"123456\"}";
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isOk());
    }

    @Test
    public void testRegisterDuplicateUser() throws Exception {
        // 1. Tạo sẵn một user trong DB
        User user = new User();
        user.setUsername("da_ton_tai");
        user.setPassword("password");
        userRepository.save(user);

        // 2. Thử đăng ký lại chính user đó
        String userJson = "{\"username\":\"da_ton_tai\",\"password\":\"123456\"}";
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isBadRequest()); // Bây giờ nó sẽ ra 400 như ý bạn
    }
}