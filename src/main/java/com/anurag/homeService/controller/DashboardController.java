package com.anurag.homeService.controller;

import com.anurag.homeService.dto.DashboardStatsDto;
import com.anurag.homeService.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/dashboard")
@CrossOrigin(origins = "http://localhost:5173")
public class DashboardController {

      @Autowired
      private DashboardService dashboardService;

      @GetMapping("/stats")
      public ResponseEntity<DashboardStatsDto> getStats() {
            return ResponseEntity.ok(dashboardService.getStats());
      }
}
