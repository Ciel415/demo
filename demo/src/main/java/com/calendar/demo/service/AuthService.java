package com.calendar.demo.service;

import com.calendar.demo.dto.LoginRequest;
import com.calendar.demo.dto.UserRegisterRequest;
import com.calendar.demo.dto.UserResponse;
import com.calendar.demo.entity.User;
import com.calendar.demo.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse register(UserRegisterRequest req) {
        if (userRepository.findByEmail(req.getEmail()) != null) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword())); // 암호화 저장

        return new UserResponse(userRepository.save(user));
    }

    public String login(LoginRequest request, HttpServletRequest req) {
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        req.getSession().setAttribute("userId", user.getId());
        return "로그인 성공";
    }
}
