package com.example.demo;

import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@Bean
CommandLineRunner initDatabase(UserRepository userRepository, 
                             ProductRepository productRepository, 
                             PasswordEncoder passwordEncoder) {
    return args -> {
        // 1. Tạo User mẫu (nếu chưa có)
        if (userRepository.count() == 0) {
            User user = new User();
            user.setUsername("tung_user");
            user.setPassword(passwordEncoder.encode("123"));
            user.setRole("ROLE_USER");
            userRepository.save(user);

            User admin = new User();
            admin.setUsername("tung_admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRole("ROLE_ADMIN");
            userRepository.save(admin);
            System.out.println(">>> Đã tạo tài khoản: tung_user/123 và tung_admin/admin");
        }

        // 2. Tạo Sản phẩm mẫu (Khớp với Entity Product của bạn)
        if (productRepository.count() == 0) {
            Product p1 = new Product();
            p1.setName("iPhone 15 Pro");
            p1.setDescription("Màu Titan tự nhiên, 256GB");
            p1.setPrice(new BigDecimal("1000.00")); // BigDecimal cần truyền String vào
            p1.setStock(50);
            productRepository.save(p1);

            Product p2 = new Product();
            p2.setName("MacBook M3");
            p2.setDescription("Chip M3 Max, 32GB RAM");
            p2.setPrice(new BigDecimal("2500.00"));
            p2.setStock(15);
            productRepository.save(p2);
            System.out.println(">>> Đã tạo sản phẩm mẫu ID 1 và 2");
        }
    };
}
}
