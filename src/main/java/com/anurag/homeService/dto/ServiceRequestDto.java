package com.anurag.homeService.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class ServiceRequestDto {

      @NotNull(message = "Category ID is required")
      private Long categoryId;

      @NotBlank(message = "Title is required")
      private String title;

      private String description;

      @NotBlank(message = "Address is required")
      private String address;

      @NotNull(message = "Preferred date is required")
      @FutureOrPresent(message = "Preferred date must be today or later")
      private LocalDate preferredDate;

      public ServiceRequestDto() {
      }

      public Long getCategoryId() {
            return categoryId;
      }

      public void setCategoryId(Long categoryId) {
            this.categoryId = categoryId;
      }

      public String getTitle() {
            return title;
      }

      public void setTitle(String title) {
            this.title = title;
      }

      public String getDescription() {
            return description;
      }

      public void setDescription(String description) {
            this.description = description;
      }

      public String getAddress() {
            return address;
      }

      public void setAddress(String address) {
            this.address = address;
      }

      public LocalDate getPreferredDate() {
            return preferredDate;
      }

      public void setPreferredDate(LocalDate preferredDate) {
            this.preferredDate = preferredDate;
      }
}
