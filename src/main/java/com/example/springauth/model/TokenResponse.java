package com.example.springauth.model;

import lombok.Builder;

@Builder
public record TokenResponse(String token) { }
