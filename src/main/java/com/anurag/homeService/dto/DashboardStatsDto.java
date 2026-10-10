package com.anurag.homeService.dto;

public class DashboardStatsDto {

      private long totalUsers;
      private long totalProviders;
      private long totalRequests;
      private long pending;
      private long assigned;
      private long inProgress;
      private long completed;
      private long cancelled;

      public DashboardStatsDto() {
      }

      public DashboardStatsDto(long totalUsers, long totalProviders, long totalRequests, long pending, long assigned, long inProgress, long completed, long cancelled) {
            this.totalUsers = totalUsers;
            this.totalProviders = totalProviders;
            this.totalRequests = totalRequests;
            this.pending = pending;
            this.assigned = assigned;
            this.inProgress = inProgress;
            this.completed = completed;
            this.cancelled = cancelled;
      }

      public long getTotalUsers() {
            return totalUsers;
      }

      public void setTotalUsers(long totalUsers) {
            this.totalUsers = totalUsers;
      }

      public long getTotalProviders() {
            return totalProviders;
      }

      public void setTotalProviders(long totalProviders) {
            this.totalProviders = totalProviders;
      }

      public long getTotalRequests() {
            return totalRequests;
      }

      public void setTotalRequests(long totalRequests) {
            this.totalRequests = totalRequests;
      }

      public long getPending() {
            return pending;
      }

      public void setPending(long pending) {
            this.pending = pending;
      }

      public long getAssigned() {
            return assigned;
      }

      public void setAssigned(long assigned) {
            this.assigned = assigned;
      }

      public long getInProgress() {
            return inProgress;
      }

      public void setInProgress(long inProgress) {
            this.inProgress = inProgress;
      }

      public long getCompleted() {
            return completed;
      }

      public void setCompleted(long completed) {
            this.completed = completed;
      }

      public long getCancelled() {
            return cancelled;
      }

      public void setCancelled(long cancelled) {
            this.cancelled = cancelled;
      }
}
