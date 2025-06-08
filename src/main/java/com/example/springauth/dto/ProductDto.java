package com.example.springauth.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductDto {
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private UUID sellerId;
    private boolean isActive;
} 