package com.example.springauth.exception;

public class BalanceException extends BusinessException {
    public static final String INSUFFICIENT_FUNDS = "Недостаточно средств";

    public BalanceException(String message) {
        super(message, 400);
    }
} 