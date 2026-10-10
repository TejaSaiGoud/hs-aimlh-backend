package com.anurag.homeService.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CategoryDto {

      private Long id;

      @NotBlank(message = "Name is required")
      private String name;

      private String description;

      @NotNull(message = "Base price is required")
      private BigDecimal basePrice;

      public CategoryDto() {
      }

      public Long getId() {
            return id;
      }

      public void setId(Long id) {
            this.id = id;
      }

      public String getName() {
            return name;
      }

      public void setName(String name) {
            this.name = name;
      }

      public String getDescription() {
            return description;
      }

      public void setDescription(String description) {
            this.description = description;
      }

      public BigDecimal getBasePrice() {
            return basePrice;
      }

      public void setBasePrice(BigDecimal basePrice) {
            this.basePrice = basePrice;
      }
}
