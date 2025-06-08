package com.example.springauth.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductListDto {
    private Long id;
    private String title;
    private BigDecimal price;
    private UUID sellerId;
    private boolean isActive;
}
