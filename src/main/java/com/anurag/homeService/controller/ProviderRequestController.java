package com.anurag.homeService.controller;

import com.anurag.homeService.dto.ServiceRequestResponseDto;
import com.anurag.homeService.enums.RequestStatus;
import com.anurag.homeService.service.ServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/provider/requests")
@CrossOrigin(origins = "http://localhost:5173")
public class ProviderRequestController {

      @Autowired
      private ServiceRequestService requestService;

      @GetMapping("/available")
      public ResponseEntity<List<ServiceRequestResponseDto>> listAvailableRequests() {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            return ResponseEntity.ok(requestService.listAvailableForProvider(email));
      }

      @PutMapping("/{id}/accept")
      public ResponseEntity<ServiceRequestResponseDto> acceptRequest(@PathVariable Long id) {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            return ResponseEntity.ok(requestService.acceptJobByProvider(email, id));
      }

      @GetMapping
      public ResponseEntity<List<ServiceRequestResponseDto>> listAssignedRequests(
                  @RequestParam(required = false) RequestStatus status) {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            return ResponseEntity.ok(requestService.listForProvider(email, status));
      }

      @PutMapping("/{id}/status")
      public ResponseEntity<ServiceRequestResponseDto> updateStatus(
                  @PathVariable Long id,
                  @RequestParam RequestStatus status) {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            return ResponseEntity.ok(requestService.updateStatusByProvider(email, id, status));
      }
}
