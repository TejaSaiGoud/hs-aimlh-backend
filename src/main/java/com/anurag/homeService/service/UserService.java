package com.anurag.homeService.service;

import com.anurag.homeService.dto.UserResponseDto;
import com.anurag.homeService.entity.User;
import com.anurag.homeService.enums.Role;
import com.anurag.homeService.exception.ResourceNotFoundException;
import com.anurag.homeService.repository.ServiceRequestRepository;
import com.anurag.homeService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

      @Autowired
      private UserRepository userRepository;

      @Autowired
      private ServiceRequestRepository serviceRequestRepository;

      public List<UserResponseDto> listUsers() {
            return userRepository.findByRole(Role.USER).stream()
                        .map(this::mapToDto)
                        .collect(Collectors.toList());
      }

      public void delete(Long id) {
            User user = userRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

            if (user.getRole() == Role.ADMIN) {
                  throw new IllegalArgumentException("Admin account cannot be deleted");
            }

            if (serviceRequestRepository.existsByUserId(id)) {
                  throw new IllegalArgumentException("User has service requests and cannot be deleted");
            }

            userRepository.delete(user);
      }

      private UserResponseDto mapToDto(User user) {
            UserResponseDto dto = new UserResponseDto();
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());
            dto.setPhone(user.getPhone());
            dto.setAddress(user.getAddress());
            dto.setRole(user.getRole());
            return dto;
      }
}
