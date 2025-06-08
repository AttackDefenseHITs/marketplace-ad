package com.example.springauth.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ProductPurchaseDto {
    private Long id;
    private Long productId;
    private UUID buyerId;
    private LocalDateTime purchaseDate;
} 