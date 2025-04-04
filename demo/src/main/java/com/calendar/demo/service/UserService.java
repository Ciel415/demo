package com.calendar.demo.service;

import com.calendar.demo.dto.UserRequest;
import com.calendar.demo.dto.UserResponse;
import com.calendar.demo.entity.User;
import com.calendar.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 사용자 등록
    public UserResponse register(UserRequest userRequest) {
        if (userRepository.findByEmail(userRequest.getEmail()) != null) {
            throw new IllegalArgumentException("이미 등록된 이메일입니다.");
        }

        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword()); // AuthService에서는 암호화함

        return new UserResponse(userRepository.save(user));
    }

    // 이메일로 사용자 조회
    public UserResponse findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }
        return new UserResponse(user);
    }

    // ID로 사용자 조회 (선택적으로 사용)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    // 사용자 수정
    public UserResponse update(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());

        return new UserResponse(userRepository.save(user));
    }

    // 사용자 삭제
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    // 전체 사용자 목록
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }
}
