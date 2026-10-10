package com.anurag.homeService.service;

import com.anurag.homeService.dto.LoginRequestDto;
import com.anurag.homeService.dto.LoginResponseDto;
import com.anurag.homeService.dto.RegisterRequestDto;
import com.anurag.homeService.entity.ServiceCategory;
import com.anurag.homeService.entity.User;
import com.anurag.homeService.enums.Role;
import com.anurag.homeService.exception.ResourceNotFoundException;
import com.anurag.homeService.repository.ServiceCategoryRepository;
import com.anurag.homeService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

      @Autowired
      private UserRepository userRepository;

      @Autowired
      private ServiceCategoryRepository categoryRepository;

      @Autowired
      private BCryptPasswordEncoder passwordEncoder;

      @Autowired
      private JwtService jwtService;

      public void register(RegisterRequestDto dto) {
            if (userRepository.existsByEmail(dto.getEmail())) {
                  throw new IllegalArgumentException("Email is already registered");
            }

            if (dto.getRole() == Role.ADMIN) {
                  throw new IllegalArgumentException("Admin registration is not allowed");
            }

            User user = new User();
            user.setName(dto.getName());
            user.setEmail(dto.getEmail());
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            user.setPhone(dto.getPhone());
            user.setAddress(dto.getAddress());

            if (dto.getRole() == Role.PROVIDER) {
                  if (dto.getCategoryId() == null) {
                        throw new IllegalArgumentException("Category is required for service providers");
                  }
                  ServiceCategory category = categoryRepository.findById(dto.getCategoryId())
                              .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + dto.getCategoryId()));
                  user.setRole(Role.PROVIDER);
                  user.setCategory(category);
            } else {
                  user.setRole(Role.USER);
            }

            userRepository.save(user);
      }

      public LoginResponseDto login(LoginRequestDto dto) {
            User user = userRepository.findByEmail(dto.getEmail())
                        .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

            if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
                  throw new BadCredentialsException("Invalid email or password");
            }

            String token = jwtService.generateToken(user.getEmail(), user.getRole().name());
            return new LoginResponseDto(token, user.getName(), user.getRole().name());
      }
}
