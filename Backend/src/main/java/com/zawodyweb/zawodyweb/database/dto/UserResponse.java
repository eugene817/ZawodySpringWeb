package com.zawodyweb.zawodyweb.database.dto;

import com.zawodyweb.zawodyweb.database.models.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

  private Long id;
  private String login;
  private String email;
  private String firstName;
  private String lastName;
  private String token;

  public static UserResponse toResponse(User user, String token) {
    return UserResponse.builder()
        .id(user.getId())
        .login(user.getLogin())
        .email(user.getEmail())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .token(token)
        .build();
  }
}
