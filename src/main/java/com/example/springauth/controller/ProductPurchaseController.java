package com.example.springauth.controller;

import com.example.springauth.dto.ProductPurchaseDto;
import com.example.springauth.entity.User;
import com.example.springauth.service.ProductPurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
public class ProductPurchaseController {
    private final ProductPurchaseService productPurchaseService;

    @PostMapping("/{productId}")
    public ResponseEntity<Void> purchaseProduct(
            @PathVariable Long productId,
            @AuthenticationPrincipal User user) {
        productPurchaseService.purchaseProduct(productId, user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/my")
    public ResponseEntity<List<ProductPurchaseDto>> getMyPurchases(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(productPurchaseService.getUserPurchases(user));
    }
} 