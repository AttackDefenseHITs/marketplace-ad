package com.example.springauth.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterBody(
        @Email(message = "Email should be valid") String email,
        @Pattern(regexp = "\\d{10}", message = "Phone number should be 10 digits") String phone,
        @NotBlank(message = "Password cannot be blank") @Size(min = 6, message = "Password should have at least 6 characters") String password,
        @NotBlank(message = "Nickname cannot be blank") String nickName
) { }
