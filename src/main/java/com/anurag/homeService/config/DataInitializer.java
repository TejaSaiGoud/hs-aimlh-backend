package com.anurag.homeService.config;

import com.anurag.homeService.entity.ServiceCategory;
import com.anurag.homeService.entity.User;
import com.anurag.homeService.enums.Role;
import com.anurag.homeService.repository.ServiceCategoryRepository;
import com.anurag.homeService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

      @Autowired
      private UserRepository userRepository;

      @Autowired
      private ServiceCategoryRepository categoryRepository;

      @Autowired
      private BCryptPasswordEncoder passwordEncoder;

      @Override
      public void run(String... args) throws Exception {
            seedCategories();
            seedAdmin();
      }

      private void seedCategories() {
            if (categoryRepository.count() > 0) {
                  return;
            }

            ServiceCategory plumbing = new ServiceCategory();
            plumbing.setName("Plumbing");
            plumbing.setDescription("Pipe repair, leakage fixing, tap installation, and plumbing maintenance.");
            plumbing.setBasePrice(new BigDecimal("500"));

            ServiceCategory electrical = new ServiceCategory();
            electrical.setName("Electrical");
            electrical.setDescription("Wiring, switchboard repair, appliance installation, and electrical servicing.");
            electrical.setBasePrice(new BigDecimal("600"));

            ServiceCategory cleaning = new ServiceCategory();
            cleaning.setName("Cleaning");
            cleaning.setDescription("Deep home cleaning, bathroom disinfection, sofa, and kitchen cleaning.");
            cleaning.setBasePrice(new BigDecimal("800"));

            ServiceCategory appliance = new ServiceCategory();
            appliance.setName("Appliance Maintenance");
            appliance.setDescription("Repair and maintenance for AC, refrigerators, washing machines, and ovens.");
            appliance.setBasePrice(new BigDecimal("700"));

            ServiceCategory painting = new ServiceCategory();
            painting.setName("Painting");
            painting.setDescription("Interior and exterior wall painting, touch-ups, and waterproof coating.");
            painting.setBasePrice(new BigDecimal("900"));

            ServiceCategory carpentry = new ServiceCategory();
            carpentry.setName("Carpentry");
            carpentry.setDescription("Furniture assembly, door and hinge repair, and custom woodwork.");
            carpentry.setBasePrice(new BigDecimal("650"));

            ServiceCategory pestControl = new ServiceCategory();
            pestControl.setName("Pest Control");
            pestControl.setDescription("Cockroach, termite, mosquito, and rodent treatment with safe chemicals.");
            pestControl.setBasePrice(new BigDecimal("750"));

            List<ServiceCategory> categories = Arrays.asList(plumbing, electrical, cleaning, appliance, painting,
                        carpentry, pestControl);
            categoryRepository.saveAll(categories);
      }

      private void seedAdmin() {
            if (userRepository.existsByEmail("admin@home.com")) {
                  return;
            }

            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("admin@home.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setPhone("1234567890");
            admin.setAddress("Headquarters");
            admin.setRole(Role.ADMIN);

            userRepository.save(admin);
      }
}
