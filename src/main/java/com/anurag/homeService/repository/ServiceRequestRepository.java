package com.anurag.homeService.repository;

import com.anurag.homeService.entity.ServiceRequest;
import com.anurag.homeService.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {

      List<ServiceRequest> findAllByOrderByCreatedAtDesc();

      List<ServiceRequest> findByUserIdOrderByCreatedAtDesc(Long userId);

      List<ServiceRequest> findByProviderIdOrderByCreatedAtDesc(Long providerId);

      List<ServiceRequest> findByCategoryIdAndStatusOrderByCreatedAtDesc(Long categoryId, RequestStatus status);

      boolean existsByCategoryId(Long categoryId);

      boolean existsByUserId(Long userId);

      boolean existsByProviderId(Long providerId);

      long countByStatus(RequestStatus status);
}
