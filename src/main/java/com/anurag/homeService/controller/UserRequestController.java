package com.anurag.homeService.controller;

import com.anurag.homeService.dto.ServiceRequestDto;
import com.anurag.homeService.dto.ServiceRequestResponseDto;
import com.anurag.homeService.service.ServiceRequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user/requests")
@CrossOrigin(origins = "http://localhost:5173")
public class UserRequestController {

      @Autowired
      private ServiceRequestService requestService;

      @PostMapping
      public ResponseEntity<ServiceRequestResponseDto> create(@Valid @RequestBody ServiceRequestDto dto) {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            return ResponseEntity.ok(requestService.createForUser(email, dto));
      }

      @GetMapping
      public ResponseEntity<List<ServiceRequestResponseDto>> listMyRequests() {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            return ResponseEntity.ok(requestService.listForUser(email));
      }

      @PutMapping("/{id}/cancel")
      public ResponseEntity<ServiceRequestResponseDto> cancel(@PathVariable Long id) {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            return ResponseEntity.ok(requestService.cancelByUser(email, id));
      }
}
