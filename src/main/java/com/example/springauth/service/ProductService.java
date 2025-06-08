package com.example.springauth.service;

import com.example.springauth.dto.CreateProductDto;
import com.example.springauth.dto.ProductDto;
import com.example.springauth.dto.ProductListDto;
import com.example.springauth.entity.Product;
import com.example.springauth.entity.User;
import com.example.springauth.exception.ProductException;
import com.example.springauth.mapper.ProductMapper;
import com.example.springauth.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductPurchaseService productPurchaseService;
    private final ProductMapper productMapper;

    public List<ProductListDto> getAllActiveProducts() {
        return productRepository.findByIsActiveTrue()
                .stream()
                .map(productMapper::toProduct)
                .collect(Collectors.toList());
    }

    public List<ProductDto> getUserProducts(User user) {
        return productRepository.findBySellerId(user.getId())
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductDto getProduct(Long productId, User user) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(ProductException.PRODUCT_NOT_FOUND));

        if (!product.getSeller().getId().equals(user.getId()) && 
            !productPurchaseService.hasUserPurchasedProduct(productId, user)) {
            product.setDescription("Описание доступно только после покупки");
        }

        return productMapper.toDto(product);
    }

    @Transactional
    public ProductDto createProduct(CreateProductDto dto, User seller) {

        var product = new Product();
        product.setTitle(dto.getTitle());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setSeller(seller);
        product.setActive(true);

        product.setSeller(seller);
        return productMapper.toDto(productRepository.save(product));
    }
} 