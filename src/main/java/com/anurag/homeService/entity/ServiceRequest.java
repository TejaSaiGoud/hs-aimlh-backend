package com.anurag.homeService.entity;

import com.anurag.homeService.enums.RequestStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "service_request")
public class ServiceRequest {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;

      @ManyToOne
      @JoinColumn(name = "user_id", nullable = false)
      private User user;

      @ManyToOne
      @JoinColumn(name = "category_id", nullable = false)
      private ServiceCategory category;

      @ManyToOne
      @JoinColumn(name = "provider_id", nullable = true)
      private User provider;

      @Column(nullable = false)
      private String title;

      @Column(length = 1000)
      private String description;

      @Column(nullable = false)
      private String address;

      private LocalDate preferredDate;

      @Enumerated(EnumType.STRING)
      private RequestStatus status;

      private LocalDateTime createdAt;

      public ServiceRequest() {
      }

      @PrePersist
      public void prePersist() {
            if (this.createdAt == null) {
                  this.createdAt = LocalDateTime.now();
            }
      }

      public Long getId() {
            return id;
      }

      public void setId(Long id) {
            this.id = id;
      }

      public User getUser() {
            return user;
      }

      public void setUser(User user) {
            this.user = user;
      }

      public ServiceCategory getCategory() {
            return category;
      }

      public void setCategory(ServiceCategory category) {
            this.category = category;
      }

      public User getProvider() {
            return provider;
      }

      public void setProvider(User provider) {
            this.provider = provider;
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

      public RequestStatus getStatus() {
            return status;
      }

      public void setStatus(RequestStatus status) {
            this.status = status;
      }

      public LocalDateTime getCreatedAt() {
            return createdAt;
      }

      public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
      }
}
