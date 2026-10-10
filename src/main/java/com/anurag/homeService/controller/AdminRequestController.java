package com.anurag.homeService.controller;

import com.anurag.homeService.dto.ServiceRequestResponseDto;
import com.anurag.homeService.enums.RequestStatus;
import com.anurag.homeService.service.ServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/requests")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminRequestController {

      @Autowired
      private ServiceRequestService requestService;

      @GetMapping
      public ResponseEntity<List<ServiceRequestResponseDto>> listAll(
                  @RequestParam(required = false) RequestStatus status,
                  @RequestParam(required = false) String search) {
            return ResponseEntity.ok(requestService.listAll(status, search));
      }

      @PutMapping("/{id}/assign")
      public ResponseEntity<ServiceRequestResponseDto> assign(
                  @PathVariable Long id,
                  @RequestParam Long providerId) {
            return ResponseEntity.ok(requestService.assign(id, providerId));
      }

      @PutMapping("/{id}/cancel")
      public ResponseEntity<ServiceRequestResponseDto> cancel(@PathVariable Long id) {
            return ResponseEntity.ok(requestService.cancelByAdmin(id));
      }

      @DeleteMapping("/{id}")
      public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
            requestService.delete(id);
            return ResponseEntity.ok(Collections.singletonMap("message", "Service request deleted successfully"));
      }
}
