package com.memorybottle.memory_app.controller;

import com.memorybottle.memory_app.common.Result;
import com.memorybottle.memory_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.memorybottle.memory_app.domain.User;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin
public class LoginController {

    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String name) {
        System.out.println(name);
        User user = userRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("用户名不存在"));

        return ResponseEntity.ok(Result.success(user));

    }
}