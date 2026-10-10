package com.anurag.homeService.service;

import com.anurag.homeService.dto.DashboardStatsDto;
import com.anurag.homeService.enums.RequestStatus;
import com.anurag.homeService.enums.Role;
import com.anurag.homeService.repository.ServiceRequestRepository;
import com.anurag.homeService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

      @Autowired
      private UserRepository userRepository;

      @Autowired
      private ServiceRequestRepository serviceRequestRepository;

      public DashboardStatsDto getStats() {
            long totalUsers = userRepository.findByRole(Role.USER).size();
            long totalProviders = userRepository.findByRole(Role.PROVIDER).size();
            long totalRequests = serviceRequestRepository.count();

            long pending = serviceRequestRepository.countByStatus(RequestStatus.PENDING);
            long assigned = serviceRequestRepository.countByStatus(RequestStatus.ASSIGNED);
            long inProgress = serviceRequestRepository.countByStatus(RequestStatus.IN_PROGRESS);
            long completed = serviceRequestRepository.countByStatus(RequestStatus.COMPLETED);
            long cancelled = serviceRequestRepository.countByStatus(RequestStatus.CANCELLED);

            return new DashboardStatsDto(
                        totalUsers,
                        totalProviders,
                        totalRequests,
                        pending,
                        assigned,
                        inProgress,
                        completed,
                        cancelled
            );
      }
}
