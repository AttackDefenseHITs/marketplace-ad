package com.example.springauth.mapper;

import com.example.springauth.dto.ProductDto;
import com.example.springauth.dto.ProductListDto;
import com.example.springauth.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    
    public ProductDto toDto(Product product) {
        if (product == null) {
            return null;
        }
        
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setSellerId(product.getSeller().getId());
        dto.setActive(product.isActive());
        
        return dto;
    }

    public ProductListDto toProduct(Product product) {
        if (product == null) {
            return null;
        }
        ProductListDto dto = new ProductListDto();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setActive(product.isActive());
        dto.setPrice(product.getPrice());
        dto.setSellerId(product.getSeller().getId());
        return dto;
    }
    
    public Product toEntity(ProductDto dto) {
        if (dto == null) {
            return null;
        }
        
        Product product = new Product();
        product.setId(dto.getId());
        product.setTitle(dto.getTitle());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setActive(dto.isActive());
        
        return product;
    }
} 