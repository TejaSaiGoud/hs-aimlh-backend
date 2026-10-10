package com.anurag.homeService.controller;

import com.anurag.homeService.dto.CategoryDto;
import com.anurag.homeService.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoryController {

      @Autowired
      private CategoryService categoryService;

      @GetMapping
      public ResponseEntity<List<CategoryDto>> getAll() {
            return ResponseEntity.ok(categoryService.getAll());
      }

      @PostMapping
      public ResponseEntity<CategoryDto> add(@Valid @RequestBody CategoryDto dto) {
            return ResponseEntity.ok(categoryService.add(dto));
      }

      @PutMapping("/{id}")
      public ResponseEntity<CategoryDto> update(@PathVariable Long id, @Valid @RequestBody CategoryDto dto) {
            return ResponseEntity.ok(categoryService.update(id, dto));
      }

      @DeleteMapping("/{id}")
      public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
            categoryService.delete(id);
            return ResponseEntity.ok(Collections.singletonMap("message", "Category deleted successfully"));
      }
}
