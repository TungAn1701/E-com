package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Bắt các lỗi RuntimeException (như cái lỗi "Sản phẩm không tồn tại" bạn viết)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) // Trả về 400 thay vì 500
                .body(Map.of(
                    "status", "error",
                    "message", e.getMessage()
                ));
    }

    // Bạn có thể bắt thêm các lỗi khác như AccessDenied (403)
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(Map.of("message", "Bạn không có quyền truy cập!"));
    }
}