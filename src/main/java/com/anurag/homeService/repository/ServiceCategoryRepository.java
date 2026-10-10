package com.anurag.homeService.repository;

import com.anurag.homeService.entity.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Long> {

      boolean existsByName(String name);

      boolean existsByNameAndIdNot(String name, Long id);
}
