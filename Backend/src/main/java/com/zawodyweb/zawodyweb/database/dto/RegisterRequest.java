package com.zawodyweb.zawodyweb.database.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

  @NotBlank
  @Size(min = 3, max = 64)
  private String login;

  @NotBlank
  @Email
  @Size(max = 120)
  private String email;

  @NotBlank
  @Size(min = 6, max = 100)
  private String password;

  @Size(max = 40)
  private String firstName;

  @Size(max = 40)
  private String lastName;
}
