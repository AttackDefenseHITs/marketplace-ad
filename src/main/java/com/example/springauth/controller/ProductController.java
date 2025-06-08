package com.example.springauth.controller;

import com.example.springauth.dto.CreateProductDto;
import com.example.springauth.dto.ProductDto;
import com.example.springauth.dto.ProductListDto;
import com.example.springauth.entity.User;
import com.example.springauth.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductListDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllActiveProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(productService.getProduct(id, user));
    }

    @GetMapping("/my")
    public ResponseEntity<List<ProductDto>> getMyProducts(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(productService.getUserProducts(user));
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody CreateProductDto dto,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(productService.createProduct(dto, user));
    }
} 