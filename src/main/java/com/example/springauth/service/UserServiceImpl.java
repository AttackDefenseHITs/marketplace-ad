package com.example.springauth.service;

import com.example.springauth.entity.User;
import com.example.springauth.jwt.JwtTokenUtils;
import com.example.springauth.mapper.UserMapper;
import com.example.springauth.model.LoginBody;
import com.example.springauth.model.RegisterBody;
import com.example.springauth.model.TokenResponse;
import com.example.springauth.dto.UserDto;
import com.example.springauth.repository.RedisRepository;
import com.example.springauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final UserRepository userRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final RedisRepository redisRepository;

    public TokenResponse registerUser(RegisterBody body) {
        User user = UserMapper.mapRegisterBodyToUser(body);
        userRepository.save(user);

        return new TokenResponse(jwtTokenUtils.generateToken(user));
    }

    public UserDto getUserResponseByAuthentication(Authentication authentication) {
        UUID id = jwtTokenUtils.getUserIdFromAuthentication(authentication);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User with ID: " + id + " not found"));

        return UserMapper.mapUserToResponse(user);
    }

    public TokenResponse loginUser(LoginBody body){
        User user = userRepository.findByEmail(body.email())
                .filter(u -> Objects.equals(body.password(), u.getPassword()))
                .orElse(null);

        if (user == null){
            throw new UsernameNotFoundException("Invalid login details");
        }

        return new TokenResponse(jwtTokenUtils.generateToken(user));
    }

    public ResponseEntity<?> logoutUser(Authentication authentication){
        User user = getUserByAuthentication(authentication);

        if (user == null){
            throw new UsernameNotFoundException("Invalid login details");
        }

        String token = authentication.getCredentials().toString();
        String id = jwtTokenUtils.getIdFromToken(token);
        long time = jwtTokenUtils.getRemainingTimeInMillis(token);
        redisRepository.save(id, token, time);

        return ResponseEntity.ok().build();
    }

    public User getUserByAuthentication(Authentication authentication) {
        UUID id = jwtTokenUtils.getUserIdFromAuthentication(authentication);

        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User with ID: " + id + " not found"));
    }

    public UserDto getUserByNickName(String nickName) {
        User user = userRepository.findByNickName(nickName)
                .orElseThrow(() -> new UsernameNotFoundException("User with name: " + nickName + " not found"));

        return new UserDto(
                user.getId(),
                user.getNickName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole(),
                user.getRegistrationDate(),
                user.getLastUpdateDate()
        );
    }
}
