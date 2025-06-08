package com.example.springauth.service;

import com.example.springauth.dto.ProductPurchaseDto;
import com.example.springauth.entity.Product;
import com.example.springauth.entity.ProductPurchase;
import com.example.springauth.entity.User;
import com.example.springauth.exception.ProductException;
import com.example.springauth.mapper.ProductPurchaseMapper;
import com.example.springauth.repository.ProductPurchaseRepository;
import com.example.springauth.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductPurchaseService {
    private final ProductRepository productRepository;
    private final ProductPurchaseRepository productPurchaseRepository;
    private final UserBalanceService userBalanceService;
    private final ProductPurchaseMapper productPurchaseMapper;

    @Transactional
    public void purchaseProduct(Long productId, User buyer) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(ProductException.PRODUCT_NOT_FOUND));

        if (!product.isActive()) {
            throw new ProductException(ProductException.PRODUCT_NOT_AVAILABLE);
        }

        if (product.getSeller().getId().equals(buyer.getId())) {
            throw new ProductException(ProductException.CANNOT_PURCHASE_OWN_PRODUCT);
        }

        userBalanceService.withdrawFunds(buyer, product.getPrice());

        ProductPurchase purchase = new ProductPurchase();
        purchase.setProduct(product);
        purchase.setBuyer(buyer);
        productPurchaseRepository.save(purchase);

        product.setActive(false);
        productRepository.save(product);
    }

    public boolean hasUserPurchasedProduct(Long productId, User user) {
        return productPurchaseRepository.existsByProductIdAndBuyerId(productId, user.getId());
    }

    public List<Product> getPurchasedProducts(User user) {
        return productPurchaseRepository.findByBuyerId(user.getId())
                .stream()
                .map(ProductPurchase::getProduct)
                .collect(Collectors.toList());
    }

    public List<ProductPurchaseDto> getUserPurchases(User user) {
        return productPurchaseRepository.findByBuyerId(user.getId())
                .stream()
                .map(productPurchaseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductPurchaseDto createPurchase(Product product, User buyer) {
        ProductPurchase purchase = new ProductPurchase();
        purchase.setProduct(product);
        purchase.setBuyer(buyer);
        return productPurchaseMapper.toDto(productPurchaseRepository.save(purchase));
    }
} 