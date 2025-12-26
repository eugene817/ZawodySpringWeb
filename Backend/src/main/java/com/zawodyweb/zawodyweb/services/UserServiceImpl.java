package com.zawodyweb.zawodyweb.services;

import com.zawodyweb.zawodyweb.database.dto.AuthenticationRequest;
import com.zawodyweb.zawodyweb.database.dto.RegisterRequest;
import com.zawodyweb.zawodyweb.database.dto.UserResponse;
import com.zawodyweb.zawodyweb.database.enums.Role;
import com.zawodyweb.zawodyweb.database.models.User;
import com.zawodyweb.zawodyweb.database.repositories.UserRepository;
import com.zawodyweb.zawodyweb.exceptions.BusinessException;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  @Override
  public UserResponse register(RegisterRequest request) {
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new BusinessException("User already exists with this email");
    }
    var user = toUser(request);
    user.setRegisteredAt(Instant.now());
    userRepository.save(user);
    return UserResponse.toResponse(user, jwtService.generateToken(user));
  }

  @Override
  public UserResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
    var user =
        userRepository
            .findByLogin(request.getLogin())
            .orElseThrow(() -> new BusinessException("User not found"));
    return UserResponse.toResponse(user, jwtService.generateToken(user));
  }

  @Override
  public UserResponse getThreadUser() {
    var auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth != null && auth.getPrincipal() instanceof User user) {
      return UserResponse.toResponse(user, "token");
    }
    throw new BusinessException("User not found");
  }

  private User toUser(RegisterRequest request) {
    return User.builder()
        .login(request.getLogin())
        .email(request.getEmail())
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .passwordHash(passwordEncoder.encode(request.getPassword()))
        .roles(Set.of(Role.ROLE_USER))
        .enabled(true)
        .build();
  }
}
