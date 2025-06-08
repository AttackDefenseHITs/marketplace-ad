package com.example.springauth.controller;

import com.example.springauth.model.LoginBody;
import com.example.springauth.model.RegisterBody;
import com.example.springauth.model.TokenResponse;
import com.example.springauth.dto.UserDto;
import com.example.springauth.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @PostMapping("login")
    public ResponseEntity<TokenResponse> loginUser(@RequestBody LoginBody body){
        return ResponseEntity.ok(userService.loginUser(body));
    }

    @PostMapping("register")
    public ResponseEntity<TokenResponse> registerUser(@Valid @RequestBody RegisterBody body) {
        return ResponseEntity.ok(userService.registerUser(body));
    }

    @PostMapping("logout")
    public ResponseEntity<?> logout(Authentication authentication){
        return ResponseEntity.ok(userService.logoutUser(authentication));
    }

    @GetMapping("profile")
    public ResponseEntity<UserDto> getUser(Authentication authentication) {
        return ResponseEntity.ok(userService.getUserResponseByAuthentication(authentication));
    }

    @GetMapping("/admin/profile/{nickName}")
    public ResponseEntity<UserDto> getUserByNickname(@PathVariable String nickName) {
        return ResponseEntity.ok(userService.getUserByNickName(nickName));
    }
}
