package com.example.springauth.service;

import com.example.springauth.dto.UserBalanceDto;
import com.example.springauth.entity.User;
import com.example.springauth.entity.UserBalance;
import com.example.springauth.exception.BalanceException;
import com.example.springauth.mapper.UserBalanceMapper;
import com.example.springauth.repository.UserBalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserBalanceService {
    private final UserBalanceRepository userBalanceRepository;
    private final UserBalanceMapper userBalanceMapper;

    @Transactional
    public UserBalanceDto createBalance(User user) {
        UserBalance balance = new UserBalance();
        balance.setUser(user);
        return userBalanceMapper.toDto(userBalanceRepository.save(balance));
    }

    @Transactional
    public UserBalanceDto getUserBalance(User user) {
        return userBalanceRepository.findByUserId(user.getId())
                .map(userBalanceMapper::toDto)
                .orElseGet(() -> createBalance(user));
    }

    @Transactional
    public UserBalanceDto withdrawFunds(User user, BigDecimal amount) {
        UserBalance balance = userBalanceRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    UserBalance newBalance = new UserBalance();
                    newBalance.setUser(user);
                    return userBalanceRepository.save(newBalance);
                });
        balance.setBalance(balance.getBalance().subtract(amount));
        return userBalanceMapper.toDto(userBalanceRepository.save(balance));
    }
} 