package com.example.springauth.repository;

import com.example.springauth.entity.UserBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface UserBalanceRepository extends JpaRepository<UserBalance, Long> {
    Optional<UserBalance> findByUserId(UUID userId);
} 