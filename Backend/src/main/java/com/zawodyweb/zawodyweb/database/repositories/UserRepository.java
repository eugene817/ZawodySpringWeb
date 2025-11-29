package com.zawodyweb.zawodyweb.database.repositories;

import com.zawodyweb.zawodyweb.database.models.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByLogin(String login);

  Optional<User> findByEmail(String email);

  boolean existsByLogin(String login);

  boolean existsByEmail(String email);
}
