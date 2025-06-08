package com.example.springauth.model;

public record LoginBody(
        String email,
        String password
) {
}
