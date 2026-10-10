package com.anurag.homeService.controller;

import com.anurag.homeService.dto.CategoryDto;
import com.anurag.homeService.dto.LoginRequestDto;
import com.anurag.homeService.dto.LoginResponseDto;
import com.anurag.homeService.dto.RegisterRequestDto;
import com.anurag.homeService.service.AuthService;
import com.anurag.homeService.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

      @Autowired
      private AuthService authService;

      @Autowired
      private CategoryService categoryService;

      @PostMapping("/register")
      public ResponseEntity<Map<String, String>> register(@Valid @RequestBody RegisterRequestDto dto) {
            authService.register(dto);
            return ResponseEntity.ok(Collections.singletonMap("message", "User registered successfully"));
      }

      @PostMapping("/login")
      public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto dto) {
            LoginResponseDto response = authService.login(dto);
            return ResponseEntity.ok(response);
      }

      @GetMapping("/categories")
      public ResponseEntity<List<CategoryDto>> getPublicCategories() {
            return ResponseEntity.ok(categoryService.getAll());
      }
}
