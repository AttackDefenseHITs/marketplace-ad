package com.example.springauth.exception;

public class ProductException extends BusinessException {
    public static final String PRODUCT_NOT_FOUND = "Товар не найден";
    public static final String PRODUCT_NOT_AVAILABLE = "Товар недоступен";
    public static final String CANNOT_PURCHASE_OWN_PRODUCT = "Нельзя купить свой собственный товар";

    public ProductException(String message) {
        super(message, 400);
    }
} 