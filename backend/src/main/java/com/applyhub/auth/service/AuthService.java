package com.applyhub.auth.service;                                                                                                                                                                                
                                                                                                                                                                                                                    
import com.applyhub.auth.dto.SignupRequest;                                                                                                                                                                       
import com.applyhub.user.repository.UserRepository;                                                                                                                                                               
import lombok.RequiredArgsConstructor;                                                                                                                                                                            
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.applyhub.user.domain.User;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public void signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }
        User user = new User(request.email(), passwordEncoder.encode(request.password()), request.nickname());
        userRepository.save(user);
    }
}