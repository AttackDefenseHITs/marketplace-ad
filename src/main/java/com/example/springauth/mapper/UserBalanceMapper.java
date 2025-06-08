package com.example.springauth.mapper;

import com.example.springauth.dto.UserBalanceDto;
import com.example.springauth.entity.UserBalance;
import org.springframework.stereotype.Component;

@Component
public class UserBalanceMapper {
    
    public UserBalanceDto toDto(UserBalance userBalance) {
        if (userBalance == null) {
            return null;
        }
        
        UserBalanceDto dto = new UserBalanceDto();
        dto.setId(userBalance.getId());
        dto.setUserId(userBalance.getUser().getId());
        dto.setBalance(userBalance.getBalance());
        
        return dto;
    }
    
    public UserBalance toEntity(UserBalanceDto dto) {
        if (dto == null) {
            return null;
        }
        
        UserBalance userBalance = new UserBalance();
        userBalance.setId(dto.getId());
        userBalance.setBalance(dto.getBalance());
        
        return userBalance;
    }
} 