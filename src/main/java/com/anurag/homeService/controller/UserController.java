package com.anurag.homeService.controller;

import com.anurag.homeService.dto.UserResponseDto;
import com.anurag.homeService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

      @Autowired
      private UserService userService;

      @GetMapping
      public ResponseEntity<List<UserResponseDto>> listUsers() {
            return ResponseEntity.ok(userService.listUsers());
      }

      @DeleteMapping("/{id}")
      public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
            userService.delete(id);
            return ResponseEntity.ok(Collections.singletonMap("message", "User deleted successfully"));
      }
}
