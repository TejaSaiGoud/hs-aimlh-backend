package com.anurag.homeService.service;

import com.anurag.homeService.dto.ProviderDto;
import com.anurag.homeService.entity.ServiceCategory;
import com.anurag.homeService.entity.User;
import com.anurag.homeService.enums.Role;
import com.anurag.homeService.exception.ResourceNotFoundException;
import com.anurag.homeService.repository.ServiceCategoryRepository;
import com.anurag.homeService.repository.ServiceRequestRepository;
import com.anurag.homeService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProviderService {

      @Autowired
      private UserRepository userRepository;

      @Autowired
      private ServiceCategoryRepository categoryRepository;

      @Autowired
      private ServiceRequestRepository serviceRequestRepository;

      @Autowired
      private BCryptPasswordEncoder passwordEncoder;

      public List<ProviderDto> listProviders(Long categoryId) {
            List<User> providers = userRepository.findByRole(Role.PROVIDER);
            if (categoryId != null) {
                  providers = providers.stream()
                              .filter(p -> p.getCategory() != null && categoryId.equals(p.getCategory().getId()))
                              .collect(Collectors.toList());
            }
            return providers.stream()
                        .map(this::mapToDto)
                        .collect(Collectors.toList());
      }

      public ProviderDto create(ProviderDto dto) {
            if (userRepository.existsByEmail(dto.getEmail())) {
                  throw new IllegalArgumentException("Email is already registered");
            }

            if (dto.getCategoryId() == null) {
                  throw new IllegalArgumentException("Category is required for a provider");
            }

            if (dto.getPassword() == null || dto.getPassword().trim().isEmpty()) {
                  throw new IllegalArgumentException("Password is required for a provider");
            }

            ServiceCategory category = categoryRepository.findById(dto.getCategoryId())
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + dto.getCategoryId()));

            User provider = new User();
            provider.setName(dto.getName());
            provider.setEmail(dto.getEmail());
            provider.setPassword(passwordEncoder.encode(dto.getPassword()));
            provider.setPhone(dto.getPhone());
            provider.setRole(Role.PROVIDER);
            provider.setCategory(category);

            User saved = userRepository.save(provider);
            return mapToDto(saved);
      }

      public void delete(Long id) {
            User provider = userRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Provider not found with id: " + id));

            if (provider.getRole() != Role.PROVIDER) {
                  throw new IllegalArgumentException("User is not a provider");
            }

            if (serviceRequestRepository.existsByProviderId(id)) {
                  throw new IllegalArgumentException("Provider has assigned requests and cannot be deleted");
            }

            userRepository.delete(provider);
      }

      private ProviderDto mapToDto(User provider) {
            ProviderDto dto = new ProviderDto();
            dto.setId(provider.getId());
            dto.setName(provider.getName());
            dto.setEmail(provider.getEmail());
            dto.setPhone(provider.getPhone());
            if (provider.getCategory() != null) {
                  dto.setCategoryId(provider.getCategory().getId());
                  dto.setCategoryName(provider.getCategory().getName());
            }
            return dto;
      }
}
