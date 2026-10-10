package com.anurag.homeService.controller;

import com.anurag.homeService.dto.ProviderDto;
import com.anurag.homeService.service.ProviderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/providers")
@CrossOrigin(origins = "http://localhost:5173")
public class ProviderController {

      @Autowired
      private ProviderService providerService;

      @GetMapping
      public ResponseEntity<List<ProviderDto>> listProviders(@RequestParam(required = false) Long categoryId) {
            return ResponseEntity.ok(providerService.listProviders(categoryId));
      }

      @PostMapping
      public ResponseEntity<ProviderDto> create(@Valid @RequestBody ProviderDto dto) {
            return ResponseEntity.ok(providerService.create(dto));
      }

      @DeleteMapping("/{id}")
      public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
            providerService.delete(id);
            return ResponseEntity.ok(Collections.singletonMap("message", "Provider deleted successfully"));
      }
}
