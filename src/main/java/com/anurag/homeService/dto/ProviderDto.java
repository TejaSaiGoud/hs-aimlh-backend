package com.anurag.homeService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProviderDto {

      private Long id;

      @NotBlank(message = "Name is required")
      private String name;

      @NotBlank(message = "Email is required")
      @Email(message = "Invalid email format")
      private String email;

      @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
      private String password;

      private String phone;

      @NotNull(message = "Category ID is required")
      private Long categoryId;

      @JsonProperty(access = JsonProperty.Access.READ_ONLY)
      private String categoryName;

      public ProviderDto() {
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

      public String getEmail() {
            return email;
      }

      public void setEmail(String email) {
            this.email = email;
      }

      public String getPassword() {
            return password;
      }

      public void setPassword(String password) {
            this.password = password;
      }

      public String getPhone() {
            return phone;
      }

      public void setPhone(String phone) {
            this.phone = phone;
      }

      public Long getCategoryId() {
            return categoryId;
      }

      public void setCategoryId(Long categoryId) {
            this.categoryId = categoryId;
      }

      public String getCategoryName() {
            return categoryName;
      }

      public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
      }
}
