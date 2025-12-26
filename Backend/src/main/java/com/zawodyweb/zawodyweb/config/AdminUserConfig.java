package com.zawodyweb.zawodyweb.config;

import com.zawodyweb.zawodyweb.database.enums.Role;
import com.zawodyweb.zawodyweb.database.models.User;
import com.zawodyweb.zawodyweb.database.repositories.UserRepository;
import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminUserConfig {

  @Bean
  public CommandLineRunner createDefaultAdmin(
      UserRepository userRepository, PasswordEncoder passwordEncoder) {
    return args -> {
      if (userRepository.findByLogin("admin").isEmpty()) {
        User admin = new User();
        admin.setLogin("admin");
        admin.setPasswordHash(passwordEncoder.encode("123"));
        admin.setRoles(Set.of(Role.ROLE_MODERATOR));
        admin.setEmail("admin@example.com");

        userRepository.save(admin);
        System.out.println("--------------- Created: admin ------------------");
      }
    };
  }
}
