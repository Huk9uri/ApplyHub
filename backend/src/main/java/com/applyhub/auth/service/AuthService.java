package com.applyhub.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.applyhub.auth.dto.LoginRequest;
import com.applyhub.auth.dto.LoginResponse;
import com.applyhub.auth.dto.LoginUserResponse;
import com.applyhub.auth.dto.SignupRequest;
import com.applyhub.auth.exception.DuplicateEmailException;
import com.applyhub.auth.exception.InvalidLoginException;
import com.applyhub.auth.jwt.JwtTokenProvider;
import com.applyhub.user.domain.User;
import com.applyhub.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public void signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateEmailException();
        }
        User user = new User(request.email(), passwordEncoder.encode(request.password()), request.nickname());
        userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email()).orElseThrow(InvalidLoginException::new);

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new InvalidLoginException();
        }
        String accessToken = jwtTokenProvider.createAccessToken(user.getId());

        LoginUserResponse loginUser = new LoginUserResponse(user.getId(), user.getEmail(), user.getNickname());

        return new LoginResponse(accessToken, "Bearer", jwtTokenProvider.getExpirationSeconds(), loginUser);
    }


}