package com.example.springauth.controller;

import com.example.springauth.dto.UserBalanceDto;
import com.example.springauth.entity.User;
import com.example.springauth.service.UserBalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/balance")
@RequiredArgsConstructor
public class UserBalanceController {
    private final UserBalanceService userBalanceService;

    @GetMapping
    public ResponseEntity<UserBalanceDto> getBalance(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userBalanceService.getUserBalance(user));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<UserBalanceDto> withdrawFunds(
            @RequestParam BigDecimal amount,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userBalanceService.withdrawFunds(user, amount));
    }
} 