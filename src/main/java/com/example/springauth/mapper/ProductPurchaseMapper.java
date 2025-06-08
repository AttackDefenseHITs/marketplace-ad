package com.example.springauth.mapper;

import com.example.springauth.dto.ProductPurchaseDto;
import com.example.springauth.entity.ProductPurchase;
import org.springframework.stereotype.Component;

@Component
public class ProductPurchaseMapper {
    
    public ProductPurchaseDto toDto(ProductPurchase purchase) {
        if (purchase == null) {
            return null;
        }
        
        ProductPurchaseDto dto = new ProductPurchaseDto();
        dto.setId(purchase.getId());
        dto.setProductId(purchase.getProduct().getId());
        dto.setBuyerId(purchase.getBuyer().getId());
        dto.setPurchaseDate(purchase.getPurchaseDate());
        
        return dto;
    }
    
    public ProductPurchase toEntity(ProductPurchaseDto dto) {
        if (dto == null) {
            return null;
        }
        
        ProductPurchase purchase = new ProductPurchase();
        purchase.setId(dto.getId());
        purchase.setPurchaseDate(dto.getPurchaseDate());
        
        return purchase;
    }
} 