package com.anurag.homeService.service;

import com.anurag.homeService.dto.ServiceRequestDto;
import com.anurag.homeService.dto.ServiceRequestResponseDto;
import com.anurag.homeService.entity.ServiceCategory;
import com.anurag.homeService.entity.ServiceRequest;
import com.anurag.homeService.entity.User;
import com.anurag.homeService.enums.RequestStatus;
import com.anurag.homeService.enums.Role;
import com.anurag.homeService.exception.ResourceNotFoundException;
import com.anurag.homeService.repository.ServiceCategoryRepository;
import com.anurag.homeService.repository.ServiceRequestRepository;
import com.anurag.homeService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceRequestService {

      @Autowired
      private ServiceRequestRepository requestRepository;

      @Autowired
      private UserRepository userRepository;

      @Autowired
      private ServiceCategoryRepository categoryRepository;

      public ServiceRequestResponseDto createForUser(String email, ServiceRequestDto dto) {
            User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

            if (dto.getPreferredDate() != null && dto.getPreferredDate().isBefore(LocalDate.now())) {
                  throw new IllegalArgumentException("Preferred date must be today or later");
            }

            ServiceCategory category = categoryRepository.findById(dto.getCategoryId())
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + dto.getCategoryId()));

            ServiceRequest request = new ServiceRequest();
            request.setUser(user);
            request.setCategory(category);
            request.setProvider(null);
            request.setTitle(dto.getTitle());
            request.setDescription(dto.getDescription());
            request.setAddress(dto.getAddress());
            request.setPreferredDate(dto.getPreferredDate());
            request.setStatus(RequestStatus.PENDING);

            ServiceRequest saved = requestRepository.save(request);
            return mapToDto(saved);
      }

      public List<ServiceRequestResponseDto> listForUser(String email) {
            User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

            return requestRepository.findByUserIdOrderByCreatedAtDesc(user.getId()).stream()
                        .map(this::mapToDto)
                        .collect(Collectors.toList());
      }

      public ServiceRequestResponseDto cancelByUser(String email, Long id) {
            User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

            ServiceRequest request = requestRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Service request not found with id: " + id));

            if (!request.getUser().getId().equals(user.getId())) {
                  throw new IllegalArgumentException("You can only cancel your own service requests");
            }

            if (request.getStatus() != RequestStatus.PENDING && request.getStatus() != RequestStatus.ASSIGNED) {
                  throw new IllegalArgumentException("Only PENDING or ASSIGNED requests can be cancelled");
            }

            request.setStatus(RequestStatus.CANCELLED);
            ServiceRequest updated = requestRepository.save(request);
            return mapToDto(updated);
      }

      public List<ServiceRequestResponseDto> listAvailableForProvider(String email) {
            User provider = userRepository.findByEmail(email)
                        .orElseThrow(() -> new ResourceNotFoundException("Provider not found with email: " + email));

            if (provider.getCategory() == null) {
                  return List.of();
            }

            return requestRepository.findByCategoryIdAndStatusOrderByCreatedAtDesc(
                        provider.getCategory().getId(),
                        RequestStatus.PENDING
            ).stream().map(this::mapToDto).collect(Collectors.toList());
      }

      public ServiceRequestResponseDto acceptJobByProvider(String email, Long id) {
            User provider = userRepository.findByEmail(email)
                        .orElseThrow(() -> new ResourceNotFoundException("Provider not found with email: " + email));

            ServiceRequest request = requestRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Service request not found with id: " + id));

            if (request.getStatus() != RequestStatus.PENDING) {
                  throw new IllegalArgumentException("This request has already been assigned or cancelled");
            }

            if (provider.getCategory() == null || !provider.getCategory().getId().equals(request.getCategory().getId())) {
                  throw new IllegalArgumentException("Provider category does not match the service request");
            }

            request.setProvider(provider);
            request.setStatus(RequestStatus.ASSIGNED);

            ServiceRequest updated = requestRepository.save(request);
            return mapToDto(updated);
      }

      public List<ServiceRequestResponseDto> listForProvider(String email, RequestStatus status) {
            User provider = userRepository.findByEmail(email)
                        .orElseThrow(() -> new ResourceNotFoundException("Provider not found with email: " + email));

            List<ServiceRequest> requests = requestRepository.findByProviderIdOrderByCreatedAtDesc(provider.getId());

            if (status != null) {
                  requests = requests.stream()
                              .filter(r -> r.getStatus() == status)
                              .collect(Collectors.toList());
            }

            return requests.stream()
                        .map(this::mapToDto)
                        .collect(Collectors.toList());
      }

      public ServiceRequestResponseDto updateStatusByProvider(String email, Long id, RequestStatus newStatus) {
            User provider = userRepository.findByEmail(email)
                        .orElseThrow(() -> new ResourceNotFoundException("Provider not found with email: " + email));

            ServiceRequest request = requestRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Service request not found with id: " + id));

            if (request.getProvider() == null || !request.getProvider().getId().equals(provider.getId())) {
                  throw new IllegalArgumentException("You can only update requests assigned to you");
            }

            RequestStatus current = request.getStatus();
            boolean isValidTransition = (current == RequestStatus.ASSIGNED && newStatus == RequestStatus.IN_PROGRESS)
                        || (current == RequestStatus.IN_PROGRESS && newStatus == RequestStatus.COMPLETED);

            if (!isValidTransition) {
                  throw new IllegalArgumentException("Invalid status change");
            }

            request.setStatus(newStatus);
            ServiceRequest updated = requestRepository.save(request);
            return mapToDto(updated);
      }

      public List<ServiceRequestResponseDto> listAll(RequestStatus status, String search) {
            List<ServiceRequest> requests = requestRepository.findAllByOrderByCreatedAtDesc();

            if (status != null) {
                  requests = requests.stream()
                              .filter(r -> r.getStatus() == status)
                              .collect(Collectors.toList());
            }

            if (search != null && !search.trim().isEmpty()) {
                  String term = search.trim().toLowerCase();
                  requests = requests.stream()
                              .filter(r -> (r.getTitle() != null && r.getTitle().toLowerCase().contains(term))
                                          || (r.getUser() != null && r.getUser().getName() != null && r.getUser().getName().toLowerCase().contains(term)))
                              .collect(Collectors.toList());
            }

            return requests.stream()
                        .map(this::mapToDto)
                        .collect(Collectors.toList());
      }

      public ServiceRequestResponseDto assign(Long id, Long providerId) {
            ServiceRequest request = requestRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Service request not found with id: " + id));

            if (request.getStatus() != RequestStatus.PENDING && request.getStatus() != RequestStatus.ASSIGNED) {
                  throw new IllegalArgumentException("Request must be in PENDING or ASSIGNED status to assign a provider");
            }

            User provider = userRepository.findById(providerId)
                        .orElseThrow(() -> new ResourceNotFoundException("Provider not found with id: " + providerId));

            if (provider.getRole() != Role.PROVIDER) {
                  throw new IllegalArgumentException("Assigned user must have the PROVIDER role");
            }

            if (provider.getCategory() == null || !provider.getCategory().getId().equals(request.getCategory().getId())) {
                  throw new IllegalArgumentException("Provider must belong to the same category as the service request");
            }

            request.setProvider(provider);
            request.setStatus(RequestStatus.ASSIGNED);

            ServiceRequest updated = requestRepository.save(request);
            return mapToDto(updated);
      }

      public ServiceRequestResponseDto cancelByAdmin(Long id) {
            ServiceRequest request = requestRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Service request not found with id: " + id));

            if (request.getStatus() == RequestStatus.COMPLETED || request.getStatus() == RequestStatus.CANCELLED) {
                  throw new IllegalArgumentException("Cannot cancel a completed or already cancelled request");
            }

            request.setStatus(RequestStatus.CANCELLED);
            ServiceRequest updated = requestRepository.save(request);
            return mapToDto(updated);
      }

      public void delete(Long id) {
            if (!requestRepository.existsById(id)) {
                  throw new ResourceNotFoundException("Service request not found with id: " + id);
            }
            requestRepository.deleteById(id);
      }

      private ServiceRequestResponseDto mapToDto(ServiceRequest request) {
            ServiceRequestResponseDto dto = new ServiceRequestResponseDto();
            dto.setId(request.getId());
            dto.setTitle(request.getTitle());
            dto.setDescription(request.getDescription());
            dto.setAddress(request.getAddress());
            dto.setPreferredDate(request.getPreferredDate());
            dto.setStatus(request.getStatus() != null ? request.getStatus().name() : null);
            dto.setCreatedAt(request.getCreatedAt());

            if (request.getCategory() != null) {
                  dto.setCategoryId(request.getCategory().getId());
                  dto.setCategoryName(request.getCategory().getName());
            }

            if (request.getUser() != null) {
                  dto.setCustomerName(request.getUser().getName());
                  dto.setCustomerPhone(request.getUser().getPhone());
            }

            if (request.getProvider() != null) {
                  dto.setProviderId(request.getProvider().getId());
                  dto.setProviderName(request.getProvider().getName());
                  dto.setProviderPhone(request.getProvider().getPhone());
            }

            return dto;
      }
}
