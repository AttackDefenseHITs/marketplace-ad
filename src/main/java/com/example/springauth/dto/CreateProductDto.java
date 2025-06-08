package com.example.springauth.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductDto {
    private String title;
    private String description;
    private BigDecimal price;
}
