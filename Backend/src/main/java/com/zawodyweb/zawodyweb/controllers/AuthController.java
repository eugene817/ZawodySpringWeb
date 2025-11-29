package com.zawodyweb.zawodyweb.controllers;

import com.zawodyweb.zawodyweb.database.dto.AuthenticationRequest;
import com.zawodyweb.zawodyweb.database.dto.RegisterRequest;
import com.zawodyweb.zawodyweb.database.dto.UserResponse;
import com.zawodyweb.zawodyweb.database.models.User;
import com.zawodyweb.zawodyweb.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final UserService userService;

  public AuthController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
    UserResponse userResponse = userService.register(request);
    return ResponseEntity.ok(userResponse);
  }

  @PostMapping("/login")
  public ResponseEntity<UserResponse> authenticate(@RequestBody AuthenticationRequest request) {
    return ResponseEntity.ok(userService.authenticate(request));
  }

  @SecurityRequirement(name = "bearerAuth")
  @GetMapping("/me")
  public ResponseEntity<UserResponse> getCurrentUser(
      @AuthenticationPrincipal User user, @RequestHeader("Authorization") String authHeader) {
    String token = authHeader.substring(7);
    return ResponseEntity.ok(UserResponse.toResponse(user, token));
  }
}
