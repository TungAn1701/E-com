package com.example.demo.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProductRequest {
    private Long id; 
    private String name;
    private BigDecimal price;
    private String description;
    private int stock;
}
