package com.anurag.homeService.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ServiceRequestResponseDto {

      private Long id;
      private String title;
      private String description;
      private String address;
      private LocalDate preferredDate;
      private String status;
      private LocalDateTime createdAt;
      private Long categoryId;
      private String categoryName;
      private String customerName;
      private String customerPhone;
      private Long providerId;
      private String providerName;
      private String providerPhone;

      public ServiceRequestResponseDto() {
      }

      public Long getId() {
            return id;
      }

      public void setId(Long id) {
            this.id = id;
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

      public String getStatus() {
            return status;
      }

      public void setStatus(String status) {
            this.status = status;
      }

      public LocalDateTime getCreatedAt() {
            return createdAt;
      }

      public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
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

      public String getCustomerName() {
            return customerName;
      }

      public void setCustomerName(String customerName) {
            this.customerName = customerName;
      }

      public String getCustomerPhone() {
            return customerPhone;
      }

      public void setCustomerPhone(String customerPhone) {
            this.customerPhone = customerPhone;
      }

      public Long getProviderId() {
            return providerId;
      }

      public void setProviderId(Long providerId) {
            this.providerId = providerId;
      }

      public String getProviderName() {
            return providerName;
      }

      public void setProviderName(String providerName) {
            this.providerName = providerName;
      }

      public String getProviderPhone() {
            return providerPhone;
      }

      public void setProviderPhone(String providerPhone) {
            this.providerPhone = providerPhone;
      }
}
