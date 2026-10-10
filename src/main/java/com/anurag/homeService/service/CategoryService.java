package com.anurag.homeService.service;

import com.anurag.homeService.dto.CategoryDto;
import com.anurag.homeService.entity.ServiceCategory;
import com.anurag.homeService.exception.ResourceNotFoundException;
import com.anurag.homeService.repository.ServiceCategoryRepository;
import com.anurag.homeService.repository.ServiceRequestRepository;
import com.anurag.homeService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

      @Autowired
      private ServiceCategoryRepository categoryRepository;

      @Autowired
      private UserRepository userRepository;

      @Autowired
      private ServiceRequestRepository serviceRequestRepository;

      public List<CategoryDto> getAll() {
            return categoryRepository.findAll().stream()
                        .map(this::mapToDto)
                        .collect(Collectors.toList());
      }

      public CategoryDto add(CategoryDto dto) {
            if (categoryRepository.existsByName(dto.getName())) {
                  throw new IllegalArgumentException("Category name already exists");
            }

            ServiceCategory category = new ServiceCategory();
            category.setName(dto.getName());
            category.setDescription(dto.getDescription());
            category.setBasePrice(dto.getBasePrice());

            ServiceCategory saved = categoryRepository.save(category);
            return mapToDto(saved);
      }

      public CategoryDto update(Long id, CategoryDto dto) {
            ServiceCategory category = categoryRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

            if (categoryRepository.existsByNameAndIdNot(dto.getName(), id)) {
                  throw new IllegalArgumentException("Category name already exists");
            }

            category.setName(dto.getName());
            category.setDescription(dto.getDescription());
            category.setBasePrice(dto.getBasePrice());

            ServiceCategory updated = categoryRepository.save(category);
            return mapToDto(updated);
      }

      public void delete(Long id) {
            if (!categoryRepository.existsById(id)) {
                  throw new ResourceNotFoundException("Category not found with id: " + id);
            }

            if (serviceRequestRepository.existsByCategoryId(id) || userRepository.existsByCategoryId(id)) {
                  throw new IllegalArgumentException("Category is in use and cannot be deleted");
            }

            categoryRepository.deleteById(id);
      }

      private CategoryDto mapToDto(ServiceCategory category) {
            CategoryDto dto = new CategoryDto();
            dto.setId(category.getId());
            dto.setName(category.getName());
            dto.setDescription(category.getDescription());
            dto.setBasePrice(category.getBasePrice());
            return dto;
      }
}
