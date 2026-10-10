package com.anurag.homeService.repository;

import com.anurag.homeService.entity.User;
import com.anurag.homeService.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

      Optional<User> findByEmail(String email);

      boolean existsByEmail(String email);

      List<User> findByRole(Role role);

      boolean existsByCategoryId(Long categoryId);
}
