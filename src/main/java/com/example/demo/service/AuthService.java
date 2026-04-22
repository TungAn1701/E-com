package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String register(RegisterRequest request) {
      
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Tài khoản đã tồn tại!");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        
        String userRole = (request.getRole() != null) ? request.getRole() : "ROLE_USER";
        user.setRole(userRole);

        userRepository.save(user);
        return "Đăng ký thành công tài khoản: " + user.getUsername();
    }
}