package com.example.springauth.repository;

import com.example.springauth.entity.ProductPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ProductPurchaseRepository extends JpaRepository<ProductPurchase, Long> {
    List<ProductPurchase> findByBuyerId(UUID buyerId);
    boolean existsByProductIdAndBuyerId(Long productId, UUID buyerId);
} 