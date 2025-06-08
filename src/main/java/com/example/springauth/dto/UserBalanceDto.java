package com.example.springauth.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class UserBalanceDto {
    private Long id;
    private UUID userId;
    private BigDecimal balance;
} 