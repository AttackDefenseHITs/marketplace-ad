package com.example.springauth.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisRepository {
    private final RedisTemplate<String, String> redisTemplate;

    public void save(String key, String value, long lifetime) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, lifetime, TimeUnit.MILLISECONDS);
    }

    public Boolean has(String key) {
        return redisTemplate.hasKey(key);
    }

    public Object find(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(final String key) {
        redisTemplate.delete(key);
    }
}
